package org.example.pojo.vo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.collect.Sets;
import lombok.Data;
import org.example.entity.AlarmEventEntity;
import org.example.entity.AlarmIndexEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author violet
 * @since 2022/12/26
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
@Data
public class AlarmDetailVo {
    private String id;
    private Set<String> name;
    private Set<String> sourceIp;
    private Set<Integer> sourcePort;
    private Set<String> distinctIp;
    private Set<Integer> distinctPort;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double score;
    private long total;
    private String alarmOrigin;
    private String eventType;
    private String severity;
    private Set<String> attackChain;
    private Set<String> unitName;
    private String judgeType;
    private String judgeStatus;
    private Integer judgeResult;
    private List<String> influence;
    private String suggestion;
    // TODO : unsupported
    private String tags;
    private List<String> monitorIp;
    private Boolean createTask;

    public void assemFields(AlarmIndexEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.sourceIp = entity.getSourceIp();
        this.distinctIp = entity.getDistinctIp();
        this.total = entity.getTotal();
        this.judgeResult = entity.getJudgeResult();
        this.judgeStatus = entity.getJudgeStatus();
        this.judgeType = entity.getJudgeType();
        this.startTime = entity.getStartTime();
        this.severity = entity.getSeverity();
        this.endTime = entity.getEndTime();
        this.score = entity.getScore();
        this.eventType = entity.getEventType().stream().findFirst().orElse("");
        this.alarmOrigin = entity.getDeviceName().stream().findFirst().orElse("未知设备");
        this.influence = entity.getInfluence();
        this.suggestion = entity.getSuggestion();
        this.createTask = entity.getTaskStatus();
        this.monitorIp = entity.getMonitorIp();
    }

    public void assemFields(AlarmEventEntity entity) {
        if (Objects.isNull(unitName)) unitName = Sets.newHashSet();
        if (Objects.isNull(attackChain)) attackChain = Sets.newHashSet();
        if (Objects.isNull(sourcePort)) sourcePort = Sets.newHashSet();
        if (Objects.isNull(distinctPort)) distinctPort = Sets.newHashSet();
        if (Objects.nonNull(entity.getUnitName())) unitName.add(entity.getUnitName());
        if (Objects.nonNull(entity.getAttackChain())) attackChain.add(entity.getAttackChain());
        if (Objects.nonNull(entity.getSourcePort())) sourcePort.addAll(entity.getSourcePort());
        if (Objects.nonNull(entity.getDistinctPort())) distinctPort.addAll(entity.getDistinctPort());
    }

}
