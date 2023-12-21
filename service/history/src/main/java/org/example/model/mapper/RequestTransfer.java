package org.example.model.mapper;

import org.example.constant.EvidenceType;
import org.example.exception.ValueObjectCreatedException;
import org.example.model.History;
import org.example.model.MarkEvidenceCondition;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author violet
 * @since 2023/5/5
 */
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RequestTransfer {

    @Mapping(target = "createTime", expression = "java(LocalDateTime.now())")
    @Mapping(target = "toolBox", source = "tabName")
    @Mapping(target = "score", expression = "java(makeScore(condition.getType()))")
    @Mapping(target = "relationalEvent", expression = "java(new RelationalEvent(condition.getId(), condition.getKeyMessage() == null ? condition.getTabName().getJudgeToolBoxVo().getCnName() : condition.getKeyMessage(), condition.getEventTime(), condition.getType()))")
    @Mapping(target = "recordType", expression = "java(RecordType.clue)")
    @Mapping(target = "marked", source = "selected")
    @Mapping(target = "description", source = "text")
    @Mapping(target = "belongId", source = "alarmIndexingId")
    @Mapping(target = "type", source = "eventType")
    History markConditionToHistory(MarkEvidenceCondition condition) throws ValueObjectCreatedException;

    default double makeScore(EvidenceType evidenceType) {
        switch (evidenceType) {
            case EVIDENCE:
                return 100d;
            case DATA:
                return 0d;
            case CLUE:
            default:
                return 50d;
        }
    }

}
