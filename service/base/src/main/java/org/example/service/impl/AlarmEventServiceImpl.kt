package org.example.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import org.example.service.AlarmEventService
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.index.query.QueryBuilders
import org.example.constant.EsField
import org.example.constant.EsIndex
import org.example.entity.AlarmEventEntity
import org.example.exception.JudgeException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.IOException

@Service
class AlarmEventServiceImpl(
    private val client: RestHighLevelClient,
    private val esField: EsField,
    private val objectMapper: ObjectMapper,
) : AlarmEventService {

    private val log = LoggerFactory.getLogger(AlarmEventService::class.java)
    override fun selectByAlarmIndexingId(id: String): List<AlarmEventEntity> {
        val request = SearchRequest(EsIndex.ALARM_EVENT)
        request.source().query(QueryBuilders.termQuery(esField.alarM_INDEXING_ID, id))
        return try {
            val response = client.search(request, RequestOptions.DEFAULT)
            response.hits.hits.map {
                val alarmEvent = objectMapper.readValue(it.sourceAsString, AlarmEventEntity::class.java).apply {
                    this.id = it.id
                }
                alarmEvent
            }
        } catch (e: IOException) {
            log.error("查询关联告警失败, es sql: ${request.source()}")
            throw JudgeException(e.message)
        }
    }
}