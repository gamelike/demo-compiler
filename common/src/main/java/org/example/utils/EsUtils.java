package org.example.utils;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.Collection;

/**
 * @author zhao_yifan
 */
public class EsUtils {

    private final static String TIME = "TIME";

    public static void ipTermsBuilder(BoolQueryBuilder builder, Collection<String> sourceIp, Collection<String> distinctIp) {
        ipTermsBuilder(builder, sourceIp.toArray(new String[0]), distinctIp.toArray(new String[0]));
    }

    public static void ipTermsBuilder(BoolQueryBuilder builder, String[] sourceIp, String[] distinctIp) {
        /* 防止外层存在should 导致表意混淆*/
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.should(QueryBuilders.termsQuery("S_IP", sourceIp))
                .should(QueryBuilders.termsQuery("D_IP", distinctIp))
                .should(QueryBuilders.termsQuery("S_IPV6", sourceIp))
                .should(QueryBuilders.termsQuery("D_IPV6", distinctIp))
                .minimumShouldMatch(1);
        builder.must(boolQuery);
    }
}
