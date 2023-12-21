package org.example.service.mapper;


import org.example.context.HistoryClue;
import org.example.model.History;
import org.example.model.dto.HistoryDto;
import org.example.model.vo.ClueVo;
import org.example.model.vo.RecordVo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author violet
 * @since 2023/5/4
 */
@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface HistoryMapper {

    @Mapping(target = "result", source = "history.relationalEvent.evidenceType")
    @Mapping(target = "relationId", source = "history.relationalEvent.id")
    @Mapping(target = "eventTime", source = "history.relationalEvent.eventTime")
    ClueVo history2ClueVo(History history);

    RecordVo history2RecordVo(History history);

    @Mapping(target = "type", source = "history.relationalEvent.evidenceType")
    @Mapping(target = "id", source = "history.relationalEvent.id")
    HistoryClue history2HistoryClue(History history);

    @Mapping(target = "evidenceType", source = "history.relationalEvent.evidenceType")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "id", source = "history.relationalEvent.id")
    HistoryDto history2HistoryDto(History history);
}
