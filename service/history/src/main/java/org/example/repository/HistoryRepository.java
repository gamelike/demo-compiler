package org.example.repository;

import org.example.constant.RecordType;
import org.example.exception.ValueObjectCreatedException;
import org.example.model.History;
import org.example.model.vo.ToolBoxStatisticVo;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author violet
 * @since 2023/4/26
 */
@Repository
public interface HistoryRepository {
    List<History> findByBelongId(String belongId);

    List<History> findByBelongIdAndRecordTypeNotEqual(String belongId, List<RecordType> recordTypes);

    List<History> findByBelongIdAndRecordTypeEqual(String belongId, List<RecordType> recordTypes);

    History findById(String id) throws ValueObjectCreatedException;

    History findByBelongIdAndRelationId(String belongId, String relationId) throws ValueObjectCreatedException;

    List<ToolBoxStatisticVo> countGroupByToolBox(String belongId);

    /**
     * 默认提供单条插入. 批量插入默认使用异步.
     *
     * @param histories {@link History}
     */
    default void saveAll(Collection<History> histories) {
        for (History history : histories) {
            saveHistory(history);
        }
    }

    void saveHistory(History history);

    void saveHistoryAsync(History history);
}
