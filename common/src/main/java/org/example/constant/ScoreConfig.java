package org.example.constant;

public class ScoreConfig {

    private ScoreConfig(){}

    public static final Double INIT_SCORE = 0d;

    public static final Double MAX_SCORE = 100d;

    public static final Double WHITE_LIST_SCORE = 0d;

    public static final Double DEFAULT_CONFIDENCE_RATE = 0d;

    public static final String UPPER_LIMIT_RULE_ID = "6";

    public static final String LOWER_LIMIT_RULE_ID = "7";

    public static final Long ELASTICSEARCH_SCROLL_TIME_OUT_MINUTES = 3L;

    public static final Integer ELASTICSEARCH_THE_NUMBER_OF_SEARCH_HITS_TO_RETURN = 10000;

    public static final String ELASTICSEARCH_KEYWORD = ".keyword";

    public static final String SCORE_ENGINE_VERSION = "V0.8.1";
}
