package org.example.controller


import org.example.model.dto.DeletedHistory
import lombok.extern.slf4j.Slf4j
import org.example.constant.RecordType
import org.example.context.HistoryClue
import org.example.controller.mapper.HistoryAdaptorMapper
import org.example.exception.model.ResponseResult
import org.example.model.History
import org.example.model.MarkEvidenceCondition
import org.example.model.mapper.RequestTransfer
import org.example.repository.persistence.HistoryRecordRepository
import org.example.service.CoreService
import org.example.service.HistoryCollectService
import org.example.service.HistoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
@Deprecated(message = "废弃", replaceWith = ReplaceWith("historyController"))
open class DeprecateController(
    private val historyRecordRepository: HistoryRecordRepository,
    private val historyAdaptorMapper: HistoryAdaptorMapper,
    private val historyService: HistoryService,
    private val requestTransfer: RequestTransfer,
    private val coreService: CoreService,
    private val historyCollectService: HistoryCollectService
) {
    @GetMapping("judge/history")
    open fun historyList(id: String, page: String, size: String): ResponseResult {
        val historyRecords = historyRecordRepository.findByBelongId(id)
        val deleteHistories = historyRecords.filter { it.recordType.equals(RecordType.deleted) }
            .map { DeletedHistory(it.relationId, it.createTime) }.sortedByDescending { it.createTime }
        val historyVoList = historyRecords.filter { !it.recordType.equals(RecordType.record) }.filter {
            for (history in deleteHistories) {
                if (!it.recordType.equals(RecordType.deleted) && history.id == it.relationId) {
                    return@filter history.createTime.isBefore(it.createTime)
                }
            }
            !it.recordType.equals(RecordType.deleted)
        }.map { historyAdaptorMapper.historyRecord2Vo(it) }
        return ResponseResult.success(mapOf(Pair("list", historyVoList)))
    }

    @PostMapping("judge/evidence")
    open fun evidenceVo(@RequestBody condition: Condition): ResponseResult {
        val belongHistories = historyRecordRepository.findByBelongId(condition.alarmIndexingId)
        val deleteHistories = belongHistories.filter { it.recordType.equals(RecordType.deleted) }
            .map { DeletedHistory(it.relationId, it.createTime) }.sortedByDescending { it.createTime }
        val evidenceVoList = belongHistories.filter { !it.recordType.equals(RecordType.record) }.filter {
            condition.ids.contains(it.relationId)
        }.filter {
            for (history in deleteHistories) {
                if (!it.recordType.equals(RecordType.deleted) && history.id == it.relationId) {
                    return@filter history.createTime.isBefore(it.createTime)
                }
            }
            !it.recordType.equals(RecordType.deleted)
        }.map { historyAdaptorMapper.historyRecord2EvidenceVo(it) }
        return ResponseResult.success(evidenceVoList)
    }

    @PostMapping("judgeManager/mark-evidence")
    open fun markEvidence(@RequestBody markEvidenceCondition: MarkEvidenceCondition): ResponseResult {
        val history: History = requestTransfer.markConditionToHistory(markEvidenceCondition)
        val clueList = historyCollectService.getClueOrInitial(history.belongId)
        historyService.saveHistory(history)
        clueList.historyClueList.add(
            HistoryClue(
                history.relationalEvent.id,
                history.score,
                history.relationalEvent.evidenceType
            )
        )
        coreService.markOperation(markEvidenceCondition.alarmIndexingId)
        return ResponseResult.success()
    }

    @PostMapping("judgeManager/evidenceRemove")
    open fun deleteEvidence(@RequestBody condition: RemoveCondition): ResponseResult {
        val historyContext = historyService.getHistoryClue(condition.alarmIndexingId)
        val history = historyService.cancelHistory(condition.alarmIndexingId, condition.relationId)
        historyContext.historyClueList.removeIf {
            history.relationalEvent.id == condition.relationId
        }
        return ResponseResult.success()
    }


    data class Condition(val alarmIndexingId: String, val ids: Set<String>)

    data class RemoveCondition(val alarmIndexingId: String, val relationId: String)
}