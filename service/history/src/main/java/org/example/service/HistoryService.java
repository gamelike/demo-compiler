package org.example.service;


import org.example.context.HistoryContext;
import org.example.exception.ValueObjectCreatedException;
import org.example.model.History;
import org.example.model.dto.HistoryDto;
import org.example.model.request.HistoryQueryCondition;
import org.example.model.vo.BaseVo;
import org.example.model.vo.ToolBoxStatisticVo;

import java.util.Collection;
import java.util.List;

/**
 * @author violet
 * @since 2023/5/4
 */
public interface HistoryService {
    /**
     * 获取所有的历史.
     *
     * @param belongId  归属id
     * @param condition 查询条件
     * @return {@link BaseVo}
     */
    Collection<? extends BaseVo> getHistoryList(String belongId, HistoryQueryCondition condition);

    /**
     * 获取所有的线索, 并包装为Context.
     *
     * @param belongId 归属ID
     * @return {@link HistoryContext}
     */
    HistoryContext getHistoryClue(String belongId);

    History cancelHistory(String id) throws ValueObjectCreatedException;

    History cancelHistory(String belongId, String relationId) throws ValueObjectCreatedException;

    List<HistoryDto> getHistoryClueByBelongId(String belongId);

    List<History> getClueByBelongId(String id);

    /**
     * 保存历史.
     *
     * @param history {@link History}
     */
    void saveHistory(History history) throws ValueObjectCreatedException;

    /**
     * default async
     *
     * @param histories {@link History}
     */
    void saveAllHistory(Collection<History> histories) throws ValueObjectCreatedException;

    List<ToolBoxStatisticVo> countGroupByToolBox(String belongId);
}
