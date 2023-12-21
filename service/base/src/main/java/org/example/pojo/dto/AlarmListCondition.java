package org.example.pojo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author violet
 * @since 2023/5/15
 */
@Data
public class AlarmListCondition {
    private String name;
    @JsonAlias("sIp")
    private String sourceIp;
    @JsonAlias("dIp")
    private String destinationIp;
    @JsonAlias("type")
    private String type;
    @JsonAlias("status")
    private String status;
    @JsonAlias({"severity", "level"})
    private List<String> severities;
    @JsonAlias("mode")
    private String judgeType;
    @JsonAlias("result")
    private Integer judgeResult;
    @JsonAlias("fromDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @JsonAlias("toDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    private int offset = 0;
    private int limit = 5;
}
