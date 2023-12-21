package org.example.service;


import org.example.context.HistoryContext;
import org.example.exception.ValueObjectCreatedException;
import org.example.pojo.dto.judge.AlarmMergeDTO;
import org.example.pojo.dto.judge.JudgeCompleteDTO;
import org.example.pojo.vo.ScoreVo;

import java.util.Map;

/**
 * description:
 *
 * @author zhao_yifan
 * @since 2022/4/24
 */
public interface CoreService {

    /**
     * 完成研判.
     */
    Map<String, Object> completeJudge(JudgeCompleteDTO judgeResultDTO);

    /**
     * 标记为人工交互式研判
     */
    void markOperation(String id);

    /**
     * 手动归并告警
     *
     * @param alarmIndexing alarmEntity
     */
    void mergeAlarm(AlarmMergeDTO alarmIndexing);

    /**
     * 收集历史。
     *
     * @param id 归属ID. alarm indexing id
     */
    HistoryContext collections(String id);

    /**
     * get current event score and confidence.
     */
    ScoreVo getScoreAndConfidence(String id);

}
