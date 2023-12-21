package org.example.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.example.constant.EvidenceType;
import org.example.constant.JudgeUtilToolBox;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author violet
 * @since 2022/12/29
 */
@Data
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE,
        fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarkEvidenceCondition {
    private String id;
    private String alarmIndexingId;
    private JudgeUtilToolBox tabName;
    @JsonAlias("keyFeature")
    private String keyMessage;
    /**
     * 标记类型
     */
    private EvidenceType type;
    private String eventType;
    private String text;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventTime;
    private String keyFeature;
    private Boolean selected;

}
