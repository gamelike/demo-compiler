package org.example.po;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.constant.EvidenceType;
import org.example.constant.HistoryWay;
import org.example.constant.RecordType;

import java.time.LocalDateTime;

/**
 * persist history entity.
 *
 * @author violet
 * @since 2023/4/26
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.ANY, fieldVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class HistoryRecord {
    private String id; // elastic doc id.
    @JsonProperty("belong_id")
    private String belongId;
    @JsonProperty("relation_id")
    private String relationId;
    @JsonProperty("type")
    private String type;
    @JsonProperty("event_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "GMT+8")
    private LocalDateTime eventTime;
    @JsonProperty("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "GMT+8")
    private LocalDateTime createTime;
    @JsonProperty("record_type")
    private RecordType recordType;
    @JsonProperty("score")
    private double score;
    @JsonProperty("way")
    private HistoryWay way;
    @JsonProperty("description")
    private String description;
    @JsonProperty("process")
    private String process;

    @JsonProperty("evidence_type")
    private EvidenceType evidenceType;

    /* ----- 定制字段 ---- */
    @JsonProperty("key_feature")
    private String keyFeature;
    @JsonProperty("marked")
    private boolean marked;
    /* ------------------*/

    public HistoryWay getWay() {
        return recordType.getHistoryWay();
    }
}
