package org.example.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.IndexNotFoundException;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.xcontent.XContentType;
import org.example.constant.EsField;
import org.example.constant.EsIndex;
import org.example.constant.EvidenceType;
import org.example.constant.JudgeStatus;
import org.example.context.HistoryClue;
import org.example.context.HistoryContext;
import org.example.exception.JudgeException;
import org.example.pojo.dto.judge.AlarmMergeDTO;
import org.example.pojo.dto.judge.JudgeCompleteDTO;
import org.example.pojo.vo.AlarmDetailVo;
import org.example.pojo.vo.ScoreVo;
import org.example.service.AlarmManagerService;
import org.example.service.CoreService;
import org.example.service.HistoryService;
import org.example.utils.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * description: generate judge history po.
 *
 * @author zhao_yifan
 * @since 2022/4/24
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CoreServiceImpl implements CoreService {
    private final AlarmManagerService alarmManagerService;
    private final RestHighLevelClient rhl;
    private final ObjectMapper objectMapper;
    private final HistoryService historyService;
    private final EsField esField;
    private final String status = "人机协同研判";

    @Override
    @SneakyThrows
    public Map<String, Object> completeJudge(JudgeCompleteDTO bulkRequestDTO) {
        Map<String, Object> resMap = new HashMap<>();

        if (bulkRequestDTO.getId().isEmpty()) {
            throw new RuntimeException("completeJudge id is null");
        }

        // 处理自动研判是否存在 //
        String index = EsIndex.ALARM_INDEXING_MANUAL;
        // fix me: performance
        GetRequest request = new GetRequest(index);
        try {
            request.id(bulkRequestDTO.getId());
            GetResponse getResponse = rhl.get(request, RequestOptions.DEFAULT);
            if (getResponse == null || !getResponse.isExists()) {
                index = EsIndex.ALARM_INDEXING_AUTO;
            }
        } catch (IOException e) {
            log.error("完成研判查询错误: DSL: {}", request);
            index = EsIndex.ALARM_INDEXING_AUTO;
        } catch (IndexNotFoundException e) {
            log.error("不存在索引 {}, 更改 {} 索引", EsIndex.ALARM_INDEXING_MANUAL, EsIndex.ALARM_INDEXING_AUTO);
            index = EsIndex.ALARM_INDEXING_AUTO;
        }

        // update es status //////////////////////////////////
        UpdateRequest updateRequest = new UpdateRequest(index, bulkRequestDTO.getId());

        // update field ///////////////////////////////////////
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put(esField.getCUSTOM1(), "人机协同研判");
        updateMap.put(esField.getATT_RESULT(), bulkRequestDTO.getResult());
        if (StringUtils.isNotBlank(bulkRequestDTO.getSuggestion())) {
            updateMap.put(esField.getSUGGESTION(), bulkRequestDTO.getSuggestion());
        }
        updateMap.put(esField.getCREATE_TASK(), bulkRequestDTO.isCreateTask()); // 定制
        // NOTE: 全流量监控字段存入es库
        if (Objects.nonNull(bulkRequestDTO.getMonitorIpList()) && !bulkRequestDTO.getMonitorIpList().isEmpty()) {
            updateMap.put(esField.getMONITOR_IP(), bulkRequestDTO.getMonitorIpList());
        }
        List<String> influence = bulkRequestDTO.getInfluence();
        if (influence != null && !influence.isEmpty()) updateMap.put(esField.getINFLUENCE_TYPE(), influence);
        updateMap.put(esField.getALARM_INDEXING_STATUS(), JudgeStatus.COMPLETE.getStatus());
        updateRequest.doc(updateMap);

        updateRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        UpdateResponse response;
        try {
            response = rhl.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("es query : {}", updateRequest);
            log.error("完成研判错误!");
            throw new JudgeException(e.getMessage());
        }

        assert response != null;
        return resMap;
    }

    @Override
    public void markOperation(String id) {
        // 处理自动研判是否存在 //
        String index = EsIndex.ALARM_INDEXING_MANUAL;
        // fix me: performance
        GetRequest request = new GetRequest(index);
        try {
            request.id(id);
            request.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
            GetResponse getResponse = rhl.get(request, RequestOptions.DEFAULT);
            if (getResponse == null || !getResponse.isExists()) {
                index = EsIndex.ALARM_INDEXING_AUTO;
            }
        } catch (IOException e) {
            log.error("查询错误: DSL: {}", request);
            index = EsIndex.ALARM_INDEXING_AUTO;
        } catch (IndexNotFoundException e) {
            log.error("不存在索引 {}, 更改 {} 索引", EsIndex.ALARM_INDEXING_MANUAL, EsIndex.ALARM_INDEXING_AUTO);
            index = EsIndex.ALARM_INDEXING_AUTO;
        }
        Map<String, Object> map = Maps.newHashMap();
        map.put(esField.getCUSTOM1(), status);
        UpdateRequest updateRequest = new UpdateRequest(index, id);
        updateRequest.docAsUpsert();
        updateRequest.doc(map);
        try {
            UpdateResponse update = rhl.update(updateRequest, RequestOptions.DEFAULT);
            log.info("将ID为{}的事件标记为人机协同研判", update.getId());
        } catch (IOException e) {
            log.error("更改状态失败, {}", e.getMessage());
            throw new JudgeException(e.getMessage());
        }
    }

    @Override
    public void mergeAlarm(AlarmMergeDTO alarmIndexing) {
        // valid args;
        alarmIndexing.valid();
        UpdateRequest request = new UpdateRequest(EsIndex.ALARM_INDEXING_MANUAL,
                Base64Utils.encodeToString(UUIDUtils.get32UUID().getBytes(StandardCharsets.UTF_8)));
        SearchRequest alarmRequest = new SearchRequest(EsIndex.ALARM_EVENT);
        BulkRequest bulkRequest = new BulkRequest(EsIndex.ALARM_EVENT_MANUAL);
        // default size < 1000.
        alarmRequest.source()
                .query(QueryBuilders.boolQuery().must(QueryBuilders.termsQuery(esField.getE_ID(), alarmIndexing.getAlarmIdList().toArray())))
                .size(1000);
        try {
            request.doc(objectMapper.writeValueAsString(alarmIndexing), XContentType.JSON);
            request.docAsUpsert(true);
            UpdateResponse update = rhl.update(request, RequestOptions.DEFAULT);
            String id = update.getId();
            SearchResponse response = rhl.search(alarmRequest, RequestOptions.DEFAULT);
            for (SearchHit hit : response.getHits()) {
                UpdateRequest updateRequest = new UpdateRequest();
                Map<String, Object> map = hit.getSourceAsMap();
                map.put("alarm_indexing_id", id);
                updateRequest.id(Base64Utils.encodeToString(UUIDUtils.get32UUID().getBytes(StandardCharsets.UTF_8)));
                updateRequest.doc(map);
                updateRequest.docAsUpsert(true);
                bulkRequest.add(updateRequest);
            }
            if (response.getHits().getTotalHits().value != 0) {
                rhl.bulk(bulkRequest, RequestOptions.DEFAULT);
            } else {
                log.warn("手动生成告警未查询到关联alarm_event");
            }
        } catch (JsonProcessingException e) {
            log.error("序列化alarmIndexing实体错误: {}", alarmIndexing);
            throw new JudgeException(e.getMessage());
        } catch (IOException e) {
            log.error("查询es错误, DSL语句: {}", alarmRequest.source());
            throw new JudgeException(e.getMessage());
        }
    }

    @Override
    public HistoryContext collections(String id) {
        return historyService.getHistoryClue(id);
    }

    @Override
    public ScoreVo getScoreAndConfidence(String id) {
        HistoryContext historyClue = historyService.getHistoryClue(id);
        List<HistoryClue> historyClueList = historyClue.getHistoryClueList();
        if (historyClue.isHitWhite() ||
                historyClueList.stream().map(HistoryClue::getType).anyMatch(EvidenceType.DATA::equals)
        ) {
            return ScoreVo.builder()
                    .desc("误报")
                    .score(0d)
                    .build();
        }
        if (historyClueList.stream().map(HistoryClue::getType).anyMatch(EvidenceType.EVIDENCE::equals)) {
            return ScoreVo.builder()
                    .score(100d)
                    .desc("确凿证据")
                    .build();
        }
        AlarmDetailVo alarmDetailVo = alarmManagerService.selectAlarmDetailById(id); // fixme performance
        return ScoreVo.builder().desc("一般线索")
                .count(historyClueList.size())
                .score(alarmDetailVo.getScore())
                .confidence(historyClue.getInitScore())
                .build();
    }

    /////////////////////////////////////////
    /////////// private method //////////////
    /////////////////////////////////////////

}
