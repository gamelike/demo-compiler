package org.example.pojo.dto.judge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.example.exception.ArgsException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author violet
 * @since 2022/10/26
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AlarmMergeDTO {
    @JsonProperty("DISPOSE_STATUS")
    private String disposeStatus;
    @JsonProperty("ALARM_S_IP_COUNT")
    private long alarmSourceIpCount;
    @JsonProperty("ALARM_D_IP_COUNT")
    private long alarmDistinctIpCount;
    @JsonProperty("START_TIME")
    private LocalDateTime startTime;
    @JsonProperty("END_TIME")
    private LocalDateTime endTime;
    @JsonProperty("CREATE_TIME")
    private LocalDateTime createTime;
    @JsonProperty("MERGE_CH_FIELDS")
    private List<String> mergeChFields;
    @JsonProperty("ALARM_URL")
    private List<String> alarmUrl;
    @JsonProperty("ALARM_S_IP")
    private List<String> alarmSourceIp;
    @JsonProperty("ALARM_SWITCH")
    private String alarmSwitch;
    @JsonProperty("MERGE_FIELDS")
    private List<String> mergeFields;
    @JsonProperty("R_ID")
    private List<String> relationIds;
    @JsonProperty("ALARM_DOMAIN")
    private List<String> alarmDomain;
    @JsonProperty("TRIGGER_ID")
    private String triggerId;
    @JsonProperty("ALARM_D_SCOPE_COUNT")
    private long alarmDistinctScopeCount;
    @JsonProperty("ALARM_TOTAL")
    private long alarmTotal;
    @JsonProperty("TRIGGER_NAME")
    private String triggerName;
    @JsonProperty("ALARM_D_SCOPE")
    private List<String> alarmDistinctScope;
    @JsonProperty("MERGEKEY")
    private String mergeKey;
    @JsonProperty("ALARM_SEVERITY")
    private String alarmSeverity;
    @JsonProperty("ALARM_DVC_T")
    private List<String> assetType;
    @JsonProperty("ALARM_SOLU")
    private String suggestion;
    @JsonProperty("ALARM_STATUS")
    private String alarmStatus;
    @JsonProperty("DUR")
    private int dur;
    @JsonProperty("ALARM_TYPE")
    private String alarmType;
    @JsonProperty("ALARM_IS_AUTO_DISPOSE")
    private String alarmIsAutoDispose;
    @JsonProperty("ALARM_SEVERITY_ID")
    private int alarmSeverityId;
    @JsonProperty("ALARM_S_ASSN")
    private List<String> alarmSourceAssetName;
    @JsonProperty("ALARM_D_IP")
    private List<String> alarmDistinctIp;
    @JsonProperty("ALARM_EVENT_TYPE")
    private List<String> alarmEventType;
    @JsonProperty("CONFIRM_STATUS")
    private String confirmStatus;
    @JsonProperty("ALARM_NAME")
    private List<String> alarmName;
    @JsonProperty("ALARM_ID_LIST")
    private List<String> alarmIdList;

    public void valid() {
        if (Objects.isNull(alarmName)) {
            throw new ArgsException("告警名称不能为空");
        }
        if (Objects.isNull(startTime)) {
            throw new ArgsException("开始时间不能为空");
        }
        if (Objects.isNull(endTime)) {
            throw new ArgsException("结束时间不能为空");
        }
        if (alarmIdList.size() > 1000) {
            throw new ArgsException("手动归并告警id过多，请设置小于1000");
        }
    }
}