package org.example.repository;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.RecordType;
import org.example.exception.ArgsException;
import org.example.exception.ValueObjectCreatedException;
import org.example.model.History;
import org.example.model.vo.ToolBoxStatisticVo;
import org.example.po.HistoryRecord;
import org.example.repository.converter.HistoryTransferMapper;
import org.example.repository.persistence.HistoryRecordRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author violet
 * @since 2023/5/4
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class HistoryRepositoryImpl implements HistoryRepository {
    private final HistoryRecordRepository historyRecordRepository;

    private final HistoryTransferMapper historyTransferMapper;

    @Override
    public List<History> findByBelongId(String belongId) {
        List<HistoryRecord> historyRecordList = historyRecordRepository.findByBelongId(belongId);
        return historyRecordList.stream().map(record -> {
                    try {
                        return historyTransferMapper.record2History(record);
                    } catch (ValueObjectCreatedException e) {
                        throw new ArgsException(e.getMessage());
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<History> findByBelongIdAndRecordTypeNotEqual(String belongId, List<RecordType> recordTypes) {
        List<HistoryRecord> historyRecordList = historyRecordRepository.findByBelongIdAndRecordTypeNotEqual(belongId, recordTypes);
        return historyRecordList.stream().map(record -> {
                    try {
                        return historyTransferMapper.record2History(record);
                    } catch (ValueObjectCreatedException e) {
                        throw new ArgsException(e.getMessage());
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<History> findByBelongIdAndRecordTypeEqual(String belongId, List<RecordType> recordTypes) {
        List<HistoryRecord> historyRecordList = historyRecordRepository.findByBelongIdAndRecordTypeEqual(belongId, recordTypes);
        return historyRecordList.stream().map(record -> {
                    try {
                        return historyTransferMapper.record2History(record);
                    } catch (ValueObjectCreatedException e) {
                        throw new ArgsException(e.getMessage());
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public History findById(String id) throws ValueObjectCreatedException {
        HistoryRecord record = historyRecordRepository.findById(id);
        return historyTransferMapper.record2History(record);
    }

    @Override
    public History findByBelongIdAndRelationId(String belongId, String relationId) throws ValueObjectCreatedException {
        HistoryRecord record = historyRecordRepository.findByBelongIdAndRelationId(belongId, relationId);
        return historyTransferMapper.record2History(record);
    }

    @Override
    public List<ToolBoxStatisticVo> countGroupByToolBox(String belongId) {
        return historyRecordRepository.keyByToolBox(belongId);
    }

    @Override
    public void saveHistory(History history) {
        HistoryRecord historyRecord = historyTransferMapper.history2Record(history);
        try {
            historyRecordRepository.saveHistorySync(historyRecord);
        } catch (JsonProcessingException e) {
            log.error("保存历史序列化错误，错误信息为 {}", e.getMessage());
        }
    }

    @Override
    public void saveHistoryAsync(History history) {
        HistoryRecord historyRecord = historyTransferMapper.history2Record(history);
        try {
            historyRecordRepository.saveHistoryAsync(historyRecord);
        } catch (JsonProcessingException e) {
            log.error("保存历史序列化错误，错误信息为 {}", e.getMessage());
        }
    }
}
