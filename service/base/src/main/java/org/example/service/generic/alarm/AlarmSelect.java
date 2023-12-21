package org.example.service.generic.alarm;


import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.example.constant.EsField;
import org.example.constant.JudgeStatus;
import org.example.service.generic.alarm.model.QueryCondition;
import org.example.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * alarm_indexing and alarm_event_
 *
 * @author violet
 * @since 2022/11/3
 */
public interface AlarmSelect {
    /**
     * select type.
     *
     * @return {@link AlarmSelectType} search type.
     */
    AlarmSelectType type();

    /**
     * select alarmIndexingList by condition.
     */
    SearchResponse selectAlarmIndexingList(QueryCondition condition);

    /**
     * select alarmEvent by condition.
     *
     */
    SearchResponse selectAlarmEventListByAlarmIndexingId(QueryCondition condition);

    /**
     * select top aggregation.
     *
     * @return {@link SearchResponse}
     */
    SearchResponse getTopAggregations(QueryCondition condition);

    /**
     * 时间聚合通用查询
     *
     */
    SearchResponse getAggregationInterval(QueryCondition condition);

    /**
     * 单聚合查询.
     */
    SearchResponse getAggregationField(QueryCondition condition);

    /**
     * alarm_indexing condition builder.
     *
     * @param condition {@link QueryCondition}
     * @param esField   {@link EsField} elasticsearch field.
     * @return {@link SearchSourceBuilder} elasticsearch condition builder.
     */
    default SearchSourceBuilder conditionBuilder(QueryCondition condition, EsField esField) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        if (Objects.isNull(condition.getStartTime()) || Objects.isNull(condition.getEndTime())) {
            LocalDateTime now = LocalDateTime.now();
            condition.setStartTime(now.minusYears(1L)).setEndTime(now);
        }
        // filter
        boolQuery.filter(QueryBuilders.rangeQuery(esField.getSTART_TIME())
                .lte(DateUtils.localDateTime2String(condition.getEndTime()))
                .gte(DateUtils.localDateTime2String(condition.getStartTime()))
                .format("yyyy-MM-dd HH:mm:ss"));

        if (Objects.nonNull(condition.getId())) {
            boolQuery.must(QueryBuilders.termsQuery(esField.getID(), condition.getId()));
        }

        if (StringUtils.isNotEmpty(condition.getStatus())) {
            if (condition.getStatus().equals(JudgeStatus.COMPLETE.getStatus())) {
                boolQuery.must(
                        QueryBuilders.termsQuery(esField.getALARM_INDEXING_STATUS() + ".keyword", condition.getStatus()));
            } else {
                boolQuery.mustNot(
                        QueryBuilders.termsQuery(esField.getALARM_INDEXING_STATUS() + ".keyword", JudgeStatus.COMPLETE.getStatus()));
            }
        }

        if (Objects.nonNull(condition.getName())) {
            boolQuery.must(
                    QueryBuilders.wildcardQuery(esField.getALARM_NAME() + ".keyword", "*" + condition.getName() + "*"));
        }
        if (Objects.nonNull(condition.getAlarmSourceIp())) {
            boolQuery.must(QueryBuilders.termsQuery(esField.getALARM_S_IP(), condition.getAlarmSourceIp()));
        }
        if (Objects.nonNull(condition.getAlarmDistinctIp())) {
            boolQuery.must(QueryBuilders.termsQuery(esField.getALARM_D_IP(), condition.getAlarmDistinctIp()));
        }

        if (Objects.nonNull(condition.getSourceIp()) || Objects.nonNull(condition.getSourceIpV6())) {
            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            if (Objects.nonNull(condition.getSourceIp())) {
                boolQuery.should(QueryBuilders.termsQuery(esField.getS_IP(), condition.getSourceIp()));
            }
            if (Objects.nonNull(condition.getSourceIpV6())) {
                boolQuery.should(QueryBuilders.termsQuery(esField.getS_IPV6(), condition.getSourceIpV6()));
            }
            boolQuery.minimumShouldMatch(1);
            boolQuery.must(builder);
        }
        if (Objects.nonNull(condition.getDistinctIpV6()) || Objects.nonNull(condition.getDistinctIp())) {
            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            if (Objects.nonNull(condition.getDistinctIp())) {
                boolQuery.should(QueryBuilders.termsQuery(esField.getD_IP(), condition.getDistinctIp()));
            }
            if (Objects.nonNull(condition.getSourceIpV6())) {
                boolQuery.should(QueryBuilders.termsQuery(esField.getD_IPV6(), condition.getDistinctIpV6()));
            }
            boolQuery.minimumShouldMatch(1);
            boolQuery.must(builder);
        }

        if (Objects.nonNull(condition.getType())) {
            boolQuery.must(QueryBuilders.termsQuery(esField.getALARM_EVENT_TYPE(), condition.getType()));
        }

        if (Objects.nonNull(condition.getSeverity())) {
            boolQuery.must(QueryBuilders.termsQuery(esField.getALARM_SEVERITY(), condition.getSeverity()));
        }

        if (Objects.nonNull(condition.getResult())) {
            boolQuery.must(QueryBuilders.termsQuery(esField.getATT_RESULT(), condition.getResult()));
        }

        if (StringUtils.isNotEmpty(condition.getMode())) {
            if (condition.getMode().equals("人机协同研判")) {
                boolQuery.must(QueryBuilders.termsQuery(esField.getCUSTOM1() + ".keyword", condition.getMode()));
            } else {
                boolQuery.mustNot(QueryBuilders.termsQuery(esField.getCUSTOM1() + ".keyword", "人机协同研判"));
            }
        }
        // builder
        // calculate search_after count.
        int count = condition.getOffset() / 10000;
        condition.setCount(count);
        if (count > 0) {
            sourceBuilder.from(0).size(10000);
        } else {
            sourceBuilder.from(condition.getOffset()).size(condition.getLimit());
        }
        sourceBuilder.query(boolQuery)
                .sort(SortBuilders.fieldSort(esField.getSTART_TIME()).order(SortOrder.DESC))
                .sort(SortBuilders.fieldSort(esField.getEND_TIME()).order(SortOrder.DESC))
                .sort(SortBuilders.fieldSort(esField.getID()).order(SortOrder.DESC));
        return sourceBuilder;
    }
}
