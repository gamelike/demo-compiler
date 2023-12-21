package org.example.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.example.annotataion.Default;
import org.example.constant.JudgeUtilToolBox;
import org.example.constant.RecordType;
import org.example.exception.ValueObjectCreatedException;
import org.springframework.lang.Nullable;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 请使用构造方法创建，约束实体.
 *
 * @author violet
 * @since 2023/4/26
 */
@Getter
@ToString
@EqualsAndHashCode
public class History {
    private final String id;
    private final String belongId;
    private final RelationalEvent relationalEvent;
    private final RecordType recordType;
    private final String description;
    private final String process;
    private final LocalDateTime createTime;
    private final String type;
    private final JudgeUtilToolBox toolBox;
    private final double score;
    private final String keyFeature;
    private final boolean marked;

    /**
     * record created.
     *
     * @param belongId    归属ID
     * @param description 描述
     */
    public History(String belongId, String description) throws ValueObjectCreatedException {
        this(null, belongId, null, RecordType.record, description, null, null, 0d, null, false);
    }

    public History(String belongId, RelationalEvent event) throws ValueObjectCreatedException {
        this(null, belongId, event, RecordType.white, "命中白名单信息", null, JudgeUtilToolBox.whiteList, 0d, null, false);
    }

    public History(String belongId, RelationalEvent event, String description, double score) throws ValueObjectCreatedException {
        this(null, belongId, event, RecordType.confidence, description, null, null, score, null, false);
    }

    public History(String id, String belongId, @Nullable RelationalEvent relationalEvent, RecordType recordType, String description, String type, JudgeUtilToolBox toolBox, double score, String keyFeature, boolean marked) throws ValueObjectCreatedException {
        this(null, belongId, relationalEvent, recordType, description, type, toolBox, LocalDateTime.now(), score, keyFeature, marked);
    }

    @Default
    @ConstructorProperties({"id", "belongId", "relationalEvent", "recordType", "description", "type", "toolBox", "createTime", "score", "keyFeature", "marked"})
    public History(String id, String belongId, @Nullable RelationalEvent relationalEvent, RecordType recordType, String description, String type, JudgeUtilToolBox toolBox, LocalDateTime createTime, double score, String keyFeature, boolean marked) throws ValueObjectCreatedException {
        this.id = id;
        this.belongId = belongId;
        this.relationalEvent = relationalEvent;
        this.recordType = recordType;
        this.description = description;
        this.type = type;
        this.toolBox = toolBox;
        this.score = score;
        this.keyFeature = keyFeature;
        this.marked = marked;
        this.createTime = createTime;
        switch (recordType) {
            case clue:
                assert relationalEvent != null;
                this.process = String.format("使用%s工具箱进行了标注, 事件为 %s", toolBox.getJudgeToolBoxVo().getCnName(), relationalEvent.getKeyMessage());
                break;
            case confidence:
                assert relationalEvent != null;
                this.process = String.format("根据规则自动生成的%s可信度", relationalEvent.getKeyMessage());
                break;
            case tib:
                assert relationalEvent != null;
                this.process = String.format("命中威胁情报 %s", relationalEvent.getKeyMessage());
                break;
            case white:
                assert relationalEvent != null;
                this.process = String.format("命中白名单 %s", relationalEvent.getKeyMessage());
                break;
            case record:
                this.process = "关键操作信息记录";
                break;
            case deleted:
                assert relationalEvent != null;
                this.process = String.format("删除了事件为%s的线索", relationalEvent.getKeyMessage());
                break;
            default:
                throw new ValueObjectCreatedException("目前不支持类型为" + recordType.name() + "的记录");
        }
        validNpe();
        validEntity();
    }

    private void validNpe() throws ValueObjectCreatedException {
        if (Objects.isNull(belongId)) throw new ValueObjectCreatedException("历史记录创建失败, 归属ID为空");
        if (Objects.isNull(recordType)) throw new ValueObjectCreatedException("历史记录创建失败，记录类型为空");
    }

    private void validEntity() throws ValueObjectCreatedException {
        if (RecordType.record.equals(recordType) && Objects.nonNull(relationalEvent)) {
            throw new ValueObjectCreatedException("历史记录创建失败,record记录不应该存在实体");
        } else if (!RecordType.record.equals(recordType) && Objects.isNull(relationalEvent)) {
            throw new ValueObjectCreatedException("历史记录创建失败,线索记录存在实体.");
        }
    }

    public History map2Deleted() throws ValueObjectCreatedException {
        return new History(id, belongId, relationalEvent, RecordType.deleted, description, type, toolBox, LocalDateTime.now().minusSeconds(1L), 0d, null, false);
    }


}