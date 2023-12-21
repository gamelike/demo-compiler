package org.example.service.impl

import com.google.common.collect.Lists
import lombok.extern.slf4j.Slf4j
import org.example.context.HistoryContext
import org.example.exception.ValueObjectCreatedException
import org.example.model.History
import org.example.pojo.dto.AlarmPushDto
import org.example.service.AlarmManagerService
import org.example.service.HistoryCollectService
import org.example.service.HistoryService
import org.example.service.generic.alarm.AlarmSelectType
import org.example.service.mapper.HistoryMapper
import org.example.utils.DateUtils
import org.springframework.stereotype.Service

@Service
@Slf4j
class HistoryCollectServiceImpl(
    private val historyMapper: HistoryMapper,
    private val historyService: HistoryService,
    private val alarmManagerService: AlarmManagerService
) : HistoryCollectService {
    override fun getClueOrInitial(belongId: String): HistoryContext {
        if (makeSureHistory(belongId)) {
            val alarmIndex = alarmManagerService.selectAlarmEventById(belongId, AlarmSelectType.normal)
            return initialHistory(
                AlarmPushDto(
                    alarmIndex.id,
                    alarmIndex.alarmName?.toList(),
                    alarmIndex.alarmSourceIp?.toList(),
                    alarmIndex.alarmDistinctIp?.toList(),
                    listOf(alarmIndex.assetType),
                    DateUtils.string2LocalDateTime(alarmIndex.eventTime)
                )
            )
        }
        return historyService.getHistoryClue(belongId)
    }

    override fun initialHistory(alarmPushDto: AlarmPushDto): HistoryContext {
        val list = handlerTib(alarmPushDto)
        val historyClues = list.map {
            historyMapper.history2HistoryClue(
                it
            )
        }
        return HistoryContext(
            alarmPushDto.id,
            0.0,
            true,
            historyClues
        ) // history context.
    }

    override fun makeSureHistory(belongId: String): Boolean {
        val list = historyService.getHistoryList(belongId, null)
        return list.isEmpty()
    }


    @Throws(ValueObjectCreatedException::class)
    private fun handlerTib(pushDto: AlarmPushDto): List<History> {
        if (pushDto.sourceIps == null && pushDto.destinationIps == null) return emptyList()
        val tibQueryRequest: MutableList<String> = Lists.newArrayList()
        tibQueryRequest.addAll(pushDto.sourceIps)
        tibQueryRequest.addAll(pushDto.destinationIps)
        val tibHistory: MutableList<History> = Lists.newArrayList()
        historyService.saveAllHistory(tibHistory)
        return tibHistory
    }
}