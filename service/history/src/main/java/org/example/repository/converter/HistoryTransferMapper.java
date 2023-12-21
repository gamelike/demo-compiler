package org.example.repository.converter;


import org.example.constant.RecordType;
import org.example.exception.ValueObjectCreatedException;
import org.example.model.History;
import org.example.model.RelationalEvent;
import org.example.po.HistoryRecord;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;

/**
 * @author violet
 * @since 2023/5/4
 */
@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        mappingInheritanceStrategy = MappingInheritanceStrategy.EXPLICIT
)
public interface HistoryTransferMapper {
    @Mapping(target = "evidenceType", source = "history.relationalEvent.evidenceType")
    @Mapping(target = "way", ignore = true)
    @Mapping(target = "type", source = "type")
    @Mapping(target = "score", source = "score")
    @Mapping(target = "relationId", source = "history.relationalEvent.id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "eventTime", source = "history.relationalEvent.eventTime")
    HistoryRecord history2Record(History history);

    @Mapping(target = "relationalEvent", expression = "java(mapRelational(historyRecord))")
    History record2History(HistoryRecord historyRecord) throws ValueObjectCreatedException;

    default RelationalEvent mapRelational(HistoryRecord historyRecord) throws ValueObjectCreatedException {
        if (RecordType.record.equals(historyRecord.getRecordType())) {
            return null;
        } else {
            return new RelationalEvent(historyRecord.getRelationId(), null, historyRecord.getEventTime(), historyRecord.getEvidenceType());
        }
    }

}
