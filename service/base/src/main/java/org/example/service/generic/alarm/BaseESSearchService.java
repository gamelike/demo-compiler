package org.example.service.generic.alarm;


import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;

/**
 * ES检索服务
 * 作为垃圾桶，处理无法归类的ES检索请求
 */
public interface BaseESSearchService {


    GetResponse getEsDocById(String index, String id);

    SearchResponse getEsDocWildcardById(String index, String id);


}
