package org.example.service;


import org.example.pojo.dto.*;
import org.example.pojo.vo.AlarmDetailVo;
import org.example.pojo.vo.ListVO;
import org.example.pojo.vo.SourceEventVo;
import org.example.service.generic.alarm.AlarmSelectType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * alarmManager Service.
 **/
public interface AlarmManagerService {

    /**
     * 获取研判完的告警事件
     *
     * @return {@link AlarmIndexDTO}
     * @since 2022-03-14
     */
    Map<String, Object> selectAlarmIndexList(AlarmSelectType selectType, AlarmListCondition condition);

    AlarmIndexDTO selectAlarmEventById(String id, AlarmSelectType selectType);

    /**
     * 获取告警详情.
     *
     * @param id alarm index id
     */
    AlarmDetailVo selectAlarmDetailById(String id);

    /**
     * 根据id获取所有告警事件源
     *
     * @return SourceEvent & DistinctEvent
     * @since 2022-03-30
     **/
    Map<String, Object> selectSourceAndDistinctEventByIndexId(String id, AlarmSelectType selectType,
                                                              String fromDate, String toDate);

    /**
     * select by ip.
     *
     * @param ip       source  ip.
     * @param fromDate from date.
     * @param toDate   to date.
     * @return {@link SourceEventVo}
     */
    ListVO<AlarmIndexDTO> selectSourceIpEvent(String ip, String type, AlarmSelectType selectType,
                                              String fromDate, String toDate,
                                              Integer offset, Integer limit);

    /**
     * chart by ip.
     *
     * @param ip       ip.
     * @param unit     time unit.
     * @param duration time cycle.
     * @return {@link ChartDTO}
     */
    ChartDTO selectSourceIpProtocol(String ip, String type, String unit, Integer duration, AlarmSelectType selectType);


    /**
     * 列表页面 上方aggs.
     *
     * @param fromTime start time.
     * @param toTime   end time.
     * @return {@link AggsDTO}
     */
    List<AggsDTO> selectMessageSourceAggs(String fromTime, String toTime, AlarmSelectType selectType);

    /**
     * 告警管理详情页 最下方告警列表
     *
     * @param id       id alarm_indexing
     * @param fromTime fromTime
     * @param toTime   toTime
     * @return {@link AlarmEventDTO}
     */
    ListVO<AlarmEventDTO> selectAlarmList(String id, AlarmSelectType selectType,
                                          LocalDateTime fromTime, LocalDateTime toTime, int offset, int limit);


    /**
     * 开始时间.
     *
     * @return {@link AggsDTO}
     */
    List<AggsDTO> getTopAggregations(AlarmSelectType type, AlarmListCondition condition);

    /**
     * 获取所有研判中的告警,并发送事件
     *
     * @author zhaoxu
     */
     void queryAllJudgeStatusIsProcessingAlarmAndSendEvent();

}
