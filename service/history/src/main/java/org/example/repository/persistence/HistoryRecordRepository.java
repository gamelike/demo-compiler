package org.example.repository.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.Beta;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.composite.CompositeValuesSourceBuilder;
import org.elasticsearch.search.aggregations.bucket.composite.ParsedComposite;
import org.elasticsearch.search.aggregations.bucket.composite.TermsValuesSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.xcontent.XContentType;
import org.example.constant.EsIndex;
import org.example.constant.HistoryRecordMapping;
import org.example.constant.JudgeUtilToolBox;
import org.example.constant.RecordType;
import org.example.exception.ArgsException;
import org.example.exception.JudgeException;
import org.example.model.vo.ToolBoxStatisticVo;
import org.example.po.HistoryRecord;
import org.example.utils.UUIDUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @author violet
 * @since 2023/5/4
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class HistoryRecordRepository {
    private final RestHighLevelClient client;
    private final ObjectMapper objectMapper;

    public List<HistoryRecord> findByBelongId(String belongId) {
        SearchRequest request = new SearchRequest(EsIndex.JUDGE_HISTORY);
        request.source().query(QueryBuilders.termQuery(HistoryRecordMapping.belongId.getMapping(), belongId))
                .size(100)
                .sort(HistoryRecordMapping.createTime.getMapping(), SortOrder.DESC); // default single history max 100
        try {
            return getHistoryRecords(request);
        } catch (IOException e) {
            log.error("查询历史Repository错误， DSL语句: {}", request.source());
            throw new JudgeException(e.getMessage());
        }
    }

    public HistoryRecord findById(String id) {
        SearchRequest request = new SearchRequest(EsIndex.JUDGE_HISTORY);
        request.source().query(QueryBuilders.termQuery(HistoryRecordMapping.id.getMapping(), id))
                .size(1);
        try {
            return getHistoryRecords(request).stream().findFirst().orElseThrow(() -> new ArgsException("不存在id为" + id + "历史"));
        } catch (IOException e) {
            log.error("查询历史Repository错误， DSL语句: {}", request.source());
            throw new JudgeException(e.getMessage());
        }
    }

    public HistoryRecord findByBelongIdAndRelationId(String belongId, String relationId) {
        SearchRequest request = new SearchRequest(EsIndex.JUDGE_HISTORY);
        request.source().query(QueryBuilders.boolQuery().must(QueryBuilders.termQuery(HistoryRecordMapping.relationId.getMapping(), relationId))
                        .must(QueryBuilders.termQuery(HistoryRecordMapping.belongId.getMapping(), belongId)))
                .sort(HistoryRecordMapping.createTime.getMapping(), SortOrder.DESC)
                .size(1);
        try {
            return getHistoryRecords(request).stream().findFirst().orElse(null);
        } catch (IOException e) {
            log.error("查询Repository错误: DSL语句: {}", request.source());
            throw new JudgeException(e.getMessage());
        }
    }

    public List<HistoryRecord> findByBelongIdAndRecordTypeNotEqual(String belongId, List<RecordType> recordTypes) {
        SearchRequest request = new SearchRequest(EsIndex.JUDGE_HISTORY);
        request.source().query(QueryBuilders.boolQuery().must(QueryBuilders.termQuery(HistoryRecordMapping.belongId.getMapping(), belongId))
                        .mustNot(QueryBuilders.termsQuery(HistoryRecordMapping.recordType.getMapping(), recordTypes.stream().map(RecordType::name).toArray())))
                .size(100);
        try {
            return getHistoryRecords(request);
        } catch (IOException e) {
            log.error("查询历史Repository错误, DSL语句: {}", request.source());
            throw new JudgeException(e.getMessage());
        }
    }

    public List<HistoryRecord> findByBelongIdAndRecordTypeEqual(String belongId, List<RecordType> recordTypes) {
        SearchRequest request = new SearchRequest(EsIndex.JUDGE_HISTORY);
        request.source().query(QueryBuilders.boolQuery().must(QueryBuilders.termQuery(HistoryRecordMapping.belongId.getMapping(), belongId))
                        .must(QueryBuilders.termsQuery(HistoryRecordMapping.recordType.getMapping(), recordTypes.stream().map(RecordType::name).toArray())))
                .size(100);
        try {
            return getHistoryRecords(request);
        } catch (IOException e) {
            log.error("查询历史Repository错误, DSL语句: {}", request.source());
            throw new JudgeException(e.getMessage());
        }
    }

    public void saveHistorySync(HistoryRecord history) throws JsonProcessingException {
        UpdateRequest request = new UpdateRequest(makeIndex(), makeId())
                .doc(objectMapper.writeValueAsString(history), XContentType.JSON)
                .docAsUpsert(true)
                .setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        try {
            UpdateResponse update = client.update(request, RequestOptions.DEFAULT);
            log.info("更新成功, {} ", update.status());
        } catch (IOException e) {
            log.error("更新历史错误");
            throw new JudgeException(e.getMessage());
        }
    }

    public void saveHistoryAsync(HistoryRecord history) throws JsonProcessingException {
        UpdateRequest request = new UpdateRequest(makeIndex(), makeId())
                .doc(objectMapper.writeValueAsString(history), XContentType.JSON)
                .docAsUpsert(true);
        client.updateAsync(request, RequestOptions.DEFAULT, new ActionListener<UpdateResponse>() {
            @Override
            public void onResponse(UpdateResponse updateResponse) {
                log.info("更新成功, {}", updateResponse.status());
            }

            @Override
            public void onFailure(Exception e) {
                log.error("更新失败", e);
            }
        });
    }

    public List<ToolBoxStatisticVo> keyByToolBox(String id) {
        List<ToolBoxStatisticVo> toolBoxStatistics = Lists.newArrayListWithExpectedSize(JudgeUtilToolBox.values().length);

        CompositeValuesSourceBuilder<?> toolBoxBuilder = new TermsValuesSourceBuilder(HistoryRecordMapping.toolBox.getMapping())
                .field(HistoryRecordMapping.toolBox.getMapping());
        CompositeValuesSourceBuilder<?> recordTypeBuilder = new TermsValuesSourceBuilder(HistoryRecordMapping.recordType.getMapping())
                .field(HistoryRecordMapping.recordType.getMapping());

        SearchRequest request = new SearchRequest(EsIndex.JUDGE_HISTORY);
        request.source().query(
                QueryBuilders.boolQuery()
                        .must(QueryBuilders.termsQuery(HistoryRecordMapping.recordType.getMapping(), RecordType.clue.name(), RecordType.deleted.name()))
                        .must(QueryBuilders.termQuery(HistoryRecordMapping.belongId.getMapping(), id))
        ).size(0).aggregation(
                AggregationBuilders.composite("composite", Lists.newArrayList(toolBoxBuilder, recordTypeBuilder))).size(60);
        try {
            Map<String, ToolBoxStatisticVo> tempMap = Maps.newHashMap();
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            Aggregations aggregations = response.getAggregations();
            for (Aggregation aggregation : aggregations) {
                ParsedComposite composite = (ParsedComposite) aggregation;
                for (ParsedComposite.ParsedBucket bucket : composite.getBuckets()) {
                    Map<String, Object> compositeMap = bucket.getKey();
                    Object recordType = compositeMap.get(HistoryRecordMapping.recordType.getMapping());
                    String mapping = (String) compositeMap.get(HistoryRecordMapping.toolBox.getMapping());
                    ToolBoxStatisticVo staticVo = tempMap.get(mapping);
                    if (staticVo == null) {
                        staticVo = ToolBoxStatisticVo.builder().name(mapping).build();
                        tempMap.put(mapping, staticVo);
                    }
                    if (recordType.equals(RecordType.deleted.name())) {
                        staticVo.setMarkTotal(staticVo.getMarkTotal() - bucket.getDocCount());
                    } else {
                        staticVo.setMarkTotal(staticVo.getMarkTotal() + bucket.getDocCount());
                    }
                }
            }
            toolBoxStatistics.addAll(tempMap.values());
            // NOTE: fill tool box to response.
            for (JudgeUtilToolBox tool : JudgeUtilToolBox.values()) {
                if (!tool.getJudgeToolBoxVo().isStatistic()) continue;
                ToolBoxStatisticVo statistic = ToolBoxStatisticVo.builder()
                        .name(tool.name())
                        .markTotal(0L)
                        .build();
                if (!toolBoxStatistics.contains(statistic)) {
                    toolBoxStatistics.add(statistic);
                }
            }
            return toolBoxStatistics;
        } catch (IOException e) {
            log.error("查询标记的标签页错误!,DSL语句: {}", request.source());
            throw new JudgeException(e.getMessage());
        }
    }

    /**
     * 目前仅用于单元测试.
     */
    @Beta
    public void deleteHistoryAsync(String belongId) throws IOException {
        DeleteByQueryRequest deleteRequest = new DeleteByQueryRequest(makeIndex());
        deleteRequest.setQuery(QueryBuilders.termQuery(HistoryRecordMapping.belongId.getMapping(), belongId));
        client.deleteByQuery(deleteRequest, RequestOptions.DEFAULT);
    }


    //// private method


    private String makeIndex() {
        final String judgeHistoryIndex = "judge_history";
        return EsIndex.PREFIX + judgeHistoryIndex + "_" + LocalDate.now();
    }

    private String makeId() {
        return UUIDUtils.get32UUID();
    }


    private List<HistoryRecord> getHistoryRecords(SearchRequest request) throws IOException {
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        List<HistoryRecord> returnList = Lists.newArrayListWithCapacity(Math.toIntExact(response.getHits().getTotalHits().value));
        for (SearchHit hit : response.getHits().getHits()) {
            HistoryRecord historyRecord = objectMapper.readValue(hit.getSourceAsString(), HistoryRecord.class);
            historyRecord.setId(hit.getId());
            returnList.add(historyRecord);
        }
        return returnList;
    }

}
