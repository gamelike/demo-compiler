package org.example.controller

import org.example.service.HistoryCollectService
import org.example.context.HistoryClue
import org.example.exception.ArgsException
import org.example.exception.JudgeException
import org.example.exception.ValueObjectCreatedException
import org.example.exception.model.ResponseResult
import org.example.model.MarkEvidenceCondition
import org.example.model.mapper.RequestTransfer
import org.example.model.request.HistoryQueryCondition
import org.example.service.CoreService
import org.example.service.HistoryService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors

@RequestMapping("history")
@RestController
open class HistoryController(
    private val historyService: HistoryService,
    private val requestTransfer: RequestTransfer,
    private val coreService: CoreService,
    private val historyCollectService: HistoryCollectService
) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @PostMapping("list/{id}")
    open fun historyList(
        @PathVariable("id") belongId: String?,
        @RequestBody condition: HistoryQueryCondition?
    ): ResponseResult {
        return try {
            val result = historyService.getHistoryList(belongId, condition)
            ResponseResult.success(result)
        } catch (e: Exception) {
            log.error("查询历史列表错误, {}", e.message)
            throw JudgeException(e.message)
        }
    }

    @PostMapping("mark-evidence")
    open fun markEvidence(@RequestBody markEvidenceCondition: MarkEvidenceCondition): ResponseResult {
        return try {
            val history = requestTransfer.markConditionToHistory(markEvidenceCondition)
            val historyClue = historyService.getHistoryClue(history.belongId)
            historyClue.historyClueList.add(
                HistoryClue(
                    history.id,
                    history.score,
                    history.relationalEvent.evidenceType
                )
            )
            historyCollectService.getClueOrInitial(history.belongId)
            historyService.saveHistory(history)
            coreService.markOperation(history.belongId)
            ResponseResult.success()
        } catch (e: ValueObjectCreatedException) {
            log.error("参数初始化错误, {}", markEvidenceCondition)
            throw ArgsException(e.message)
        } catch (e: ArgsException) {
            log.error("参数初始化错误, {}", markEvidenceCondition)
            throw ArgsException(e.message)
        } catch (e: Exception) {
            throw JudgeException(e.message)
        }
    }

    @GetMapping("cancel-evidence/{id}")
    open fun cancelEvidence(@PathVariable id: String): ResponseResult {
        return try {
            val history = historyService.cancelHistory(id)
            val historyClue = historyService.getHistoryClue(history.belongId)
            val collect = historyClue.historyClueList.stream().filter { clue: HistoryClue -> clue.id != id }.collect(
                Collectors.toList()
            )
            historyClue.historyClueList = collect
            ResponseResult.success()
        } catch (e: ValueObjectCreatedException) {
            log.error("取消标注证据错误: {}", e.message)
            throw ArgsException(e.message)
        } catch (e: Exception) {
            throw JudgeException(e.message)
        }
    }
}