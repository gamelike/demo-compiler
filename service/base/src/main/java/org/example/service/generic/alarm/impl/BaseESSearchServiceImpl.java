package org.example.service.generic.alarm.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.example.constant.EsField;
import org.example.exception.JudgeException;
import org.example.service.generic.alarm.BaseESSearchService;
import org.example.utils.StringUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class BaseESSearchServiceImpl implements BaseESSearchService {
    private final RestHighLevelClient client;
    private final EsField esField;

    @Override
    public GetResponse getEsDocById(String index, String id) {
        if (!StringUtil.isEmpty(index) && !(index.contains("*") || index.contains(","))) {
            try {
                GetRequest request = new GetRequest(index);
                request.id(id);
                return client.get(request, RequestOptions.DEFAULT);
            } catch (Exception e) {
                log.info("根据ID**{}获取索引{}上的数据失败", id, index);
            }
        }
        return null;
    }

    @Override
    public SearchResponse getEsDocWildcardById(String index, String id) {
        SearchRequest request = new SearchRequest(index);
        request.source().query(QueryBuilders.termQuery(esField.getID(), id));
        try {
            return client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new JudgeException(e.getMessage());
        }
    }
}
