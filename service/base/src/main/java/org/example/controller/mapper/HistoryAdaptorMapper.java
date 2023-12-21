package org.example.controller.mapper;

import org.example.constant.HistoryWay;
import org.example.exception.ArgsException;
import org.example.po.HistoryRecord;
import org.example.pojo.vo.EvidenceVo;
import org.example.pojo.vo.history.JudgeHistoryVO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;

/**
 * @author violet
 * @since 2023/5/24
 */
@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        mappingInheritanceStrategy = MappingInheritanceStrategy.EXPLICIT
)
@Deprecated
public interface HistoryAdaptorMapper {

    @Mapping(target = "result", expression = "java(record.getEvidenceType().getCode())")
    @Mapping(target = "proce", source = "process")
    @Mapping(target = "name", source = "process")
    @Mapping(target = "desc", source = "description")
    @Mapping(target = "auto", expression = "java(way2auto(record.getWay()))")
    @Mapping(target = "alarmIndexingId", source = "belongId")
    @Mapping(target = "id", source = "relationId")
    @Mapping(target = "evidenceType", ignore = true)
    JudgeHistoryVO historyRecord2Vo(HistoryRecord record);

    @Mapping(target = "result", expression = "java(record.getEvidenceType().getCode())")
    @Mapping(target = "desc", source = "description")
    @Mapping(target = "id", source = "relationId")
    EvidenceVo historyRecord2EvidenceVo(HistoryRecord record);


    default int way2auto(HistoryWay way) {
        switch (way) {
            case automation:
                return 1;
            case operation:
                return 0;
            default:
                throw new ArgsException("不存在" + way + "的方法");
        }
    }

}
