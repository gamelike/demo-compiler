package org.example.pojo.vo.history;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.example.constant.RuleType;
import org.example.entity.JudgeHistory;

import java.time.LocalDateTime;

/**
 * @author violet
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class JudgeHistoryVO {

    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    private String name;

    private Integer result;

    private String proce;

    private String desc;

    private Double score;

    private String alarmIndexingId;

    private String type;

    private RuleType evidenceType;

    private Integer auto;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime eventTime;

    private String keyFeature;
    private Boolean marked;

    private JudgeHistoryVO(String id, LocalDateTime createTime, Integer result, String proce, String desc,
                           Double score, String alarmIndexingId, String type, RuleType evidenceType, Integer auto, LocalDateTime eventTime,
                           String keyFeature, Boolean marked) {
        this.id = id;
        this.createTime = createTime;
        this.result = result;
        this.proce = proce;
        this.desc = desc;
        this.score = score;
        this.alarmIndexingId = alarmIndexingId;
        this.type = type;
        this.evidenceType = evidenceType;
        this.auto = auto;
        this.eventTime = eventTime;
        this.keyFeature = keyFeature;
        this.marked = marked;
    }

    public static JudgeHistoryVO from(JudgeHistory judgeHistory) {
        return new JudgeHistoryVO()
                .setAuto(judgeHistory.getAuto())
                .setCreateTime(judgeHistory.getCreateTime())
                .setDesc(judgeHistory.getDesc())
                .setEvidenceType(judgeHistory.getEvidenceType())
                .setId(judgeHistory.getId())
                .setAlarmIndexingId(judgeHistory.getAlarmIndexingId())
                .setProce(judgeHistory.getProce())
                .setResult(judgeHistory.getResult())
                .setScore(judgeHistory.getScore())
                .setType(judgeHistory.getType())
                .setName(judgeHistory.getName())
                .setKeyFeature(judgeHistory.getKeyFeature())
                .setMarked(judgeHistory.isMarked())
                .setEventTime(judgeHistory.getEventTime());
    }

    public static JudgeHistory to(JudgeHistoryVO judgeHistoryVO) {
        return new JudgeHistory().setProce(judgeHistoryVO.getProce())
                .setId(judgeHistoryVO.getId())
                .setEvidenceType(judgeHistoryVO.getEvidenceType())
                .setScore(judgeHistoryVO.getScore())
                .setResult(judgeHistoryVO.getResult())
                .setAuto(judgeHistoryVO.getAuto())
                .setDesc(judgeHistoryVO.getDesc())
                .setAlarmIndexingId(judgeHistoryVO.getAlarmIndexingId())
                .setType(judgeHistoryVO.getType())
                .setMarked(judgeHistoryVO.getMarked())
                .setKeyFeature(judgeHistoryVO.getKeyFeature())
                .setCreateTime(judgeHistoryVO.getCreateTime())
                .setName(judgeHistoryVO.getName())
                .setEventTime(judgeHistoryVO.getEventTime());
    }
}
