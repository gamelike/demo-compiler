package org.example.service

import org.example.entity.AlarmEventEntity


/**
 * @author violet
 * @since 2023/7/5
 */
interface AlarmEventService {
    /**
     * 通过alarmIndexingId查询相关告警事件([com.topsec.judge.constant.EsIndex.ALARM_EVENT]).
     *
     * alarm_indexing -> list<alarm_event>
     */
    fun selectByAlarmIndexingId(id: String): List<AlarmEventEntity>

}