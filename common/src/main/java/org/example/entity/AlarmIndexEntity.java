package org.example.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.example.entity.custom.deserializer.LocalDateTimeDeserializer;
import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @author violet
 * @since 2022/12/26
 */
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlarmIndexEntity {
    private String id;
    @JsonAlias("ALARM_NAME")
    private Set<String> name;
    @JsonAlias("ALARM_S_IP")
    private Set<String> sourceIp;
    @JsonAlias("ALARM_D_IP")
    private Set<String> distinctIp;
    @JsonAlias("ALARM_EVENT_TYPE")
    private List<String> eventType;
    @JsonAlias("ALARM_SEVERITY")
    private String severity;
    @JsonAlias("ALARM_TOTAL")
    private long total;
    @JsonAlias("CONFIDENCE")
    private double score;
    @JsonAlias("ATT_RESULT")
    private Integer judgeResult;
    @JsonAlias("CUSTOM1")
    private String judgeType = "自动研判";
    @JsonAlias("STATUS")
    private String judgeStatus;
    @JsonAlias("SOLU")
    private String suggestion;
    @JsonAlias("INFLUENCE_TYPE")
    private List<String> influence;
    @JsonAlias("START_TIME")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "GMT+8")
    private LocalDateTime startTime;
    @JsonAlias("END_TIME")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "GMT+8")
    private LocalDateTime endTime;
    @JsonAlias("TRIGGER_NAME")
    private String origin;
    @JsonAlias("CREATE_TASK")
    private Boolean taskStatus;
    @JsonAlias("MONITOR_IP")
    private List<String> monitorIp;
    @JsonAlias("ALARM_DVC_T")
    private List<String> deviceName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AlarmIndexEntity entity = (AlarmIndexEntity) o;

        return new EqualsBuilder().append(getTotal(), entity.getTotal()).append(getScore(), entity.getScore()).append(getId(), entity.getId()).append(getName(), entity.getName()).append(getSourceIp(), entity.getSourceIp()).append(getDistinctIp(), entity.getDistinctIp()).append(getEventType(), entity.getEventType()).append(getSeverity(), entity.getSeverity()).append(getJudgeResult(), entity.getJudgeResult()).append(getJudgeType(), entity.getJudgeType()).append(getJudgeStatus(), entity.getJudgeStatus()).append(getStartTime(), entity.getStartTime()).append(getEndTime(), entity.getEndTime()).append(getOrigin(), entity.getOrigin()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getName()).append(getSourceIp()).append(getDistinctIp()).append(getEventType()).append(getSeverity()).append(getTotal()).append(getScore()).append(getJudgeResult()).append(getJudgeType()).append(getJudgeStatus()).append(getStartTime()).append(getEndTime()).append(getOrigin()).toHashCode();
    }
}
