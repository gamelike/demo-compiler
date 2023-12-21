package org.example.service.generic.alarm.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.FiltersAggregator;
import org.elasticsearch.search.aggregations.bucket.histogram.LongBounds;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.example.constant.EsField;
import org.example.constant.EsIndex;
import org.example.exception.JudgeException;
import org.example.service.generic.alarm.AlarmSelect;
import org.example.service.generic.alarm.AlarmSelectType;
import org.example.service.generic.alarm.model.QueryCondition;
import org.example.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @author violet
 * @since 2022/11/5
 */
@Service("auto_merge")
@Slf4j
@RequiredArgsConstructor
public class AutoAlarmSelectImpl implements AlarmSelect {

    private final RestHighLevelClient client;
    private final EsField esField;

    @Override
    public AlarmSelectType type() {
        return AlarmSelectType.auto_merge;
    }

    @Override
    public SearchResponse selectAlarmIndexingList(QueryCondition condition) {
        SearchRequest request = new SearchRequest(EsIndex.ALARM_INDEXING_AUTO);
        SearchSourceBuilder builder = conditionBuilder(condition, esField);
        builder.trackTotalHits(true);
        request.source(builder);
        int count = condition.getCount();
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            while (count > 0) {
                SearchHits hits = response.getHits();
                SearchHit[] searchHits = hits.getHits();
                // 根据查询结果,上一个查询的最后一条记录的id
                request.source().searchAfter(searchHits[searchHits.length - 1].getSortValues());
                response = client.search(request, RequestOptions.DEFAULT);
                count--;
            }
            return response;
        } catch (IOException e) {
            log.error("查询告警列表错误!");
            throw new JudgeException(e.getMessage());
        }
    }

    @Override
    public SearchResponse selectAlarmEventListByAlarmIndexingId(QueryCondition condition) {
        // es search ///////////////////////////////
        SearchRequest request = new SearchRequest(EsIndex.ALARM_EVENT_AUTO);

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        SearchSourceBuilder sourceBuilder = SearchSourceBuilder.searchSource().query(
                boolQuery.filter(QueryBuilders.rangeQuery(esField.getTIME())
                                .gte(DateUtils.localDateTime2String(condition.getStartTime()))
                                .lte(DateUtils.localDateTime2String(condition.getEndTime()))
                                .format("yyyy-MM-dd HH:mm:ss"))
                        .must(QueryBuilders.termsQuery(esField.getALARM_INDEXING_ID(), condition.getId()))
        ).sort(SortBuilders.fieldSort(esField.getTIME()).order(SortOrder.DESC));

        request.source(sourceBuilder.from(condition.getOffset()).size(condition.getLimit()));

        try {
            return client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("query dsl : {}", request.source());
            throw new JudgeException(e.getMessage());
        }
    }

    @Override
    public SearchResponse getTopAggregations(QueryCondition condition) {
        // es search ///////////////////////////////
        SearchRequest request = new SearchRequest(EsIndex.ALARM_INDEXING_AUTO);

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        request.source(SearchSourceBuilder.searchSource().query(boolQuery.filter(
                        QueryBuilders.rangeQuery(esField.getSTART_TIME()).format("yyyy-MM-dd HH:mm:ss")
                                .gte(DateUtils.localDateTime2String(condition.getStartTime()))
                                .lte(DateUtils.localDateTime2String(condition.getEndTime()))))
                .size(0)
                .trackTotalHits(true)
                .aggregation(
                        AggregationBuilders.cardinality(esField.getALARM_S_IP())
                                .field(esField.getALARM_S_IP()))
                .aggregation(
                        AggregationBuilders.terms(esField.getALARM_SEVERITY())
                                .field(esField.getALARM_SEVERITY()))
                .aggregation(
                        AggregationBuilders.cardinality(esField.getALARM_D_IP())
                                .field(esField.getALARM_D_IP()))
                .aggregation(
                        AggregationBuilders.cardinality(esField.getALARM_S_SCOPE())
                                .field(esField.getALARM_S_SCOPE() + ".keyword"))
                .aggregation(AggregationBuilders.cardinality(esField.getALARM_D_SCOPE())
                        .field(esField.getALARM_D_SCOPE() + ".keyword")));
        try {
            return client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("主页聚合错误");
            throw new JudgeException(e.getMessage());
        }
    }

    @Override
    public SearchResponse getAggregationInterval(QueryCondition condition) {
        SearchRequest request = new SearchRequest(EsIndex.ALARM_EVENT_AUTO);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        builder.query(boolQuery);
        if (Objects.nonNull(condition.getSourceIp()) && Objects.nonNull(condition.getSourceIpV6())) {
            boolQuery.should(QueryBuilders.termsQuery(esField.getS_IP(), condition.getSourceIp()))
                    .should(QueryBuilders.termsQuery(esField.getS_IPV6(), condition.getSourceIpV6()))
                    .minimumShouldMatch(1);
        }
        if (Objects.nonNull(condition.getDistinctIp()) && Objects.nonNull(condition.getDistinctIpV6())) {
            boolQuery.should(QueryBuilders.termsQuery(esField.getD_IP(), condition.getDistinctIp()))
                    .should(QueryBuilders.termsQuery(esField.getD_IPV6(), condition.getDistinctIpV6()))
                    .minimumShouldMatch(1);
        }
        boolQuery.filter(QueryBuilders.rangeQuery(esField.getTIME()).gte(DateUtils.localDateTime2String(condition.getStartTime()))
                .lte(DateUtils.localDateTime2String(condition.getEndTime())).format("yyyy-MM-dd HH:mm:ss"));
        request.source(builder
                .aggregation(AggregationBuilders.dateHistogram(esField.getTIME()).field(esField.getTIME())
                        .fixedInterval(condition.getInterval()).format("yyyy-MM-dd").extendedBounds(new LongBounds(
                                DateUtils.localDateTime2String(condition.getStartTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                DateUtils.localDateTime2String(condition.getEndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                        // 聚合前缀查询 ////////////////////////////////
                        .subAggregation(AggregationBuilders.filters("FILTERS",
                                new FiltersAggregator.KeyedFilter("http", QueryBuilders.prefixQuery("CO_ID_LIST", "http_log")),
                                new FiltersAggregator.KeyedFilter("dns", QueryBuilders.prefixQuery("CO_ID_LIST", "dns_log")))))
                .size(0)
        );
        try {
            return client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("查询时间聚合查询错误, DSL 语句: {}", request.source());
            throw new JudgeException(e.getMessage());
        }
    }

    @Override
    public SearchResponse getAggregationField(QueryCondition condition) {
        SearchRequest request = new SearchRequest(EsIndex.ALARM_INDEXING_AUTO);
        SearchSourceBuilder builder = conditionBuilder(condition, esField);
        request.source(builder.aggregation(
                        AggregationBuilders.terms(esField.getALARM_TYPE()).field(esField.getALARM_TYPE()))
                .size(0));
        try {
            return client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("查询时间聚合错误, DSL 语句: {}", request.source());
            throw new JudgeException(e.getMessage());
        }
    }

}
