package org.example.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * alarm index dto.
 *
 * @author zhao_yifan
 * @since 2022/3/14
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class AlarmIndexDTO extends EvidenceDTO implements Serializable {

    private String id;

    private Set<String> alarmName;

    private String alarmEventType;

    private String alarmSeverity;

    private double score;

    private Integer count;

    private String judgeStatus;

    private String judgeResult;

    private String judgeType;

    private String assetType;

    private Set<String> alarmSourceIp;

    private Set<String> alarmDistinctIp;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    private String dUnit;

    private String tag;

    private String custmo5;

    private String suggestion;

    private String alarmSwitch;

    private List<String> influence;

    // 来源
    private String alarmType;

    private String triggerId;

    private String triggerName;
    private boolean taskStatus;
    private boolean flowMonitorStatus;
    private List<String> monitorIp;
    private String origin;
}
