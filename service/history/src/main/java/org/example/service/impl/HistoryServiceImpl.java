package org.example.service.impl;

import com.google.common.collect.Lists;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.RecordType;
import org.example.context.HistoryClue;
import org.example.context.HistoryContext;
import org.example.exception.ArgsException;
import org.example.exception.ValueObjectCreatedException;
import org.example.model.History;
import org.example.model.dto.DeletedHistory;
import org.example.model.dto.HistoryDto;
import org.example.model.request.HistoryQueryCondition;
import org.example.model.vo.BaseVo;
import org.example.model.vo.ToolBoxStatisticVo;
import org.example.repository.HistoryRepository;
import org.example.service.HistoryService;
import org.example.service.mapper.HistoryMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author violet
 * @since 2023/5/4
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {
    private final HistoryMapper historyMapper;
    private final HistoryRepository historyRepository;

    @Override
    public Collection<? extends BaseVo> getHistoryList(String belongId, HistoryQueryCondition condition) {
        List<History> history = historyRepository.findByBelongId(belongId); // using condition, please refactor repository.
        return history.stream().map(h -> {
            if (h.getRecordType().equals(RecordType.record)) {
                return historyMapper.history2RecordVo(h);
            } else {
                return historyMapper.history2ClueVo(h);
            }
        }).collect(Collectors.toList());
    }

    @Override
    public HistoryContext getHistoryClue(String belongId) {
        List<History> histories = historyRepository.findByBelongIdAndRecordTypeNotEqual(belongId,
                Collections.unmodifiableList(Lists.newArrayList(RecordType.record)));
        boolean hitWhite = histories.stream().map(History::getRecordType).anyMatch(RecordType.white::equals);
        List<DeletedHistory> deletedClue = deleteHistorySortedDesc(histories);
        List<HistoryClue> collect = histories.stream()
                .filter(clue -> !RecordType.record.equals(clue.getRecordType()))
                .filter(clue -> !RecordType.confidence.equals(clue.getRecordType()))
                .filter(clue -> !RecordType.deleted.equals(clue.getRecordType()))
                .filter(clue -> {
                    for (DeletedHistory deletedHistory : deletedClue) {
                        if (deletedHistory.getId().equals(clue.getRelationalEvent().getId())) {
                            return deletedHistory.getCreateTime().isBefore(clue.getCreateTime());
                        }
                    }
                    return true;
                })
                .map(historyMapper::history2HistoryClue)
                .collect(Collectors.toList());
        // desc by create time.
        double score = histories.stream()
                .filter(h -> RecordType.confidence.equals(h.getRecordType())).max((a, b) -> a.getCreateTime().isEqual(b.getCreateTime()) ? 0 :
                        a.getCreateTime().isBefore(b.getCreateTime()) ? 1 : -1)
                .map(History::getScore).orElse(50d); // default 50d
        return new HistoryContext(belongId, score, hitWhite, collect);
    }


    @Override
    public History cancelHistory(String id) throws ValueObjectCreatedException {
        History history = historyRepository.findById(id);
        if (Objects.isNull(history)) {
            log.info("不存在 id : {} 历史,标记为删除无效", id);
            return null; // fast return if it doesn't mark evidence.
        }
        History deletedRecord = history.map2Deleted();
        historyRepository.saveHistory(deletedRecord);
        return deletedRecord;
    }

    @Override
    public History cancelHistory(String belongId, String relationId) throws ValueObjectCreatedException {
        History history = historyRepository.findByBelongIdAndRelationId(belongId, relationId);
        if (Objects.isNull(history)) {
            throw new ArgsException("不存在belongId = " + belongId + ",relationId = " + relationId + "的历史");
        }
        History deleteRecord = history.map2Deleted();
        historyRepository.saveHistory(deleteRecord);
        return deleteRecord;
    }

    @Override
    public List<HistoryDto> getHistoryClueByBelongId(String belongId) {
        return getClueByBelongId(belongId).stream().map(historyMapper::history2HistoryDto).collect(Collectors.toList());
    }

    @Override
    public List<History> getClueByBelongId(String id) {
        List<History> histories = historyRepository.findByBelongIdAndRecordTypeEqual(id, Lists.newArrayList(RecordType.clue, RecordType.deleted));
        List<DeletedHistory> deleteClue = deleteHistorySortedDesc(histories);
        return histories.stream()
                .filter(clue -> {
                    for (DeletedHistory deletedHistory : deleteClue) {
                        if (deletedHistory.getId().equals(clue.getRelationalEvent().getId())) {
                            return deletedHistory.getCreateTime().isBefore(clue.getCreateTime());
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }

    @SneakyThrows
    @Override
    public void saveHistory(History history) {
        makeSureNotRepeat(history.getBelongId(), history.getRelationalEvent().getId());
        TimeUnit.SECONDS.sleep(1L);
        historyRepository.saveHistory(history);
    }

    @Override
    public void saveAllHistory(Collection<History> histories) {
        for (History history : histories) {
            historyRepository.saveHistoryAsync(history);
        }
    }

    @Override
    public List<ToolBoxStatisticVo> countGroupByToolBox(String belongId) {
        return historyRepository.countGroupByToolBox(belongId);
    }


    private List<DeletedHistory> deleteHistorySortedDesc(List<History> histories) {
        return histories.stream().filter(h -> h.getRecordType().equals(RecordType.deleted))
                .map(history -> new DeletedHistory(history.getRelationalEvent().getId(), history.getCreateTime()))
                .sorted((o1, o2) -> o1.getCreateTime().isEqual(o2.getCreateTime()) ? 0 : o1.getCreateTime().isBefore(o2.getCreateTime()) ? 1 : -1)
                .collect(Collectors.toList()); // make sure that relation event only marked once.
    }

    private void makeSureNotRepeat(String belongId, String relationId) throws ValueObjectCreatedException {
        History topHistory = historyRepository.findByBelongIdAndRelationId(belongId, relationId);
        if (topHistory == null) return;
        if (!RecordType.deleted.equals(topHistory.getRecordType())) {
            cancelHistory(topHistory.getId());
        }
    }

}
