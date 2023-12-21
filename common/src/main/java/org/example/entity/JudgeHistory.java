package org.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Objects;
import org.example.constant.JudgeUtilToolBox;
import org.example.constant.RuleType;
import org.example.entity.custom.deserializer.LocalDateTimeDeserializer;
import org.example.entity.custom.serializer.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Accessors(chain = true)
@Getter
@Setter
@NoArgsConstructor
public class JudgeHistory {


    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty(value = "create_time")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty(value = "event_time")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime eventTime;

    private String name;

    private Integer result;

    private String proce;

    private String desc;

    private Double score;

    @JsonProperty(value = "alarm_indexing_id")
    private String alarmIndexingId;

    private String type;

    @JsonProperty(value = "evidence_type")
    private RuleType evidenceType;

    private Integer auto;

    @JsonProperty(value = "tool_box")
    private JudgeUtilToolBox toolBox;

    @JsonProperty("key_feature")
    private String keyFeature;
    @JsonProperty("marked")
    private boolean marked;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JudgeHistory that = (JudgeHistory) o;
        return Objects.equal(getId(), that.getId()) && Objects.equal(getAlarmIndexingId(), that.getAlarmIndexingId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getAlarmIndexingId());
    }
}