package org.example.pojo.vo

import com.fasterxml.jackson.annotation.JsonUnwrapped
import org.example.entity.AlarmEventEntity

data class AlarmEventTableVo(
    @JsonUnwrapped
    val alarmEvent: AlarmEventEntity, val alarmEventTable: List<AlarmEventTable>
)

data class AlarmEventTable(val name: String, val cnName: String, val fieldMapping: List<FieldMapping>, val sorted: Long)

data class FieldMapping(val enName: String, val cnName: String, val value: Any?)
