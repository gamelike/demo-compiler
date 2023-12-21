package org.example.pojo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author violet
 * @since 2023/5/10
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class AlarmPushDto {
    @NonNull
    private String id;
    @JsonAlias("ALARM_NAME")
    private List<String> name;
    @JsonAlias("ALARM_S_IP")
    private List<String> sourceIps;
    @JsonAlias("ALARM_D_IP")
    private List<String> destinationIps;
    @JsonAlias("ALARM_DVC_T")
    private List<String> deviceType;
    @JsonAlias("START_TIME")
    private LocalDateTime createTime;
}
