package org.example.service.generic.alarm.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.example.service.generic.alarm.AlarmSelectType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author violet
 * @since 2022/11/3
 */
@Getter
@Setter
@Accessors(chain = true)
public class QueryCondition {
    private String id;
    private AlarmSelectType selectType;
    private String name;
    private String[] alarmSourceIp;
    private String[] alarmDistinctIp;
    private String[] sourceIp;
    private String[] distinctIp;
    private String[] sourceIpV6;
    private String[] distinctIpV6;
    private String type;
    private String status;
    private String[] severity;
    private String mode;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    private int offset = 0;
    private int limit = 10;
    private int[] result;
    //
    private int count;
    private DateHistogramInterval interval;
}
