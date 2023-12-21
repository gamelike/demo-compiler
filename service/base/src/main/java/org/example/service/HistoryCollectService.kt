package org.example.service

import org.example.context.HistoryContext
import org.example.pojo.dto.AlarmPushDto

interface HistoryCollectService {
    /**
     * 获取线索上下文，如果不存在，则初始化.
     */
    fun getClueOrInitial(belongId: String): HistoryContext

   fun initialHistory(alarmPushDto: AlarmPushDto): HistoryContext

    fun makeSureHistory(belongId: String): Boolean
}