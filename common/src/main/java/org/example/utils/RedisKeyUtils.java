package org.example.utils;

/**
 * create redis key and get redis key.
 *
 * @author zhao_yifan
 */
public class RedisKeyUtils {

    protected static final String REDIS_KEY_PREFIX = "judge";

    protected static final String REDIS_KEY_SEPARATOR = ":";

    protected static final String JUDGE_CLUE_HASH = "clue";
    private static final String TIB = "tib";

    private static final String REDIS_GUIDE = "guide-init";
    private static final String REDIS_GUIDE_TAB = "guide-tab";
    private static final String HISTORY = "history";
    private static final String JUDGE_RULE = "rule";
    private static final String UPPER_AND_LIMIT = "upper-and-limit";

    private static final String CHECK_TIME = "check-time";

    public static String getJudgeRuleKey() {
        return REDIS_KEY_PREFIX + REDIS_KEY_SEPARATOR + JUDGE_RULE;
    }

    public static String getJudgeUpperAndLimitKey() {
        return REDIS_KEY_PREFIX + REDIS_KEY_SEPARATOR + UPPER_AND_LIMIT;
    }

    public static String getCalcHashKey() {
        return REDIS_KEY_PREFIX + REDIS_KEY_SEPARATOR + JUDGE_CLUE_HASH;
    }

    public static String getCalcClue(String sIp, String dIp) {
        return REDIS_KEY_PREFIX + REDIS_KEY_SEPARATOR + sIp + REDIS_KEY_SEPARATOR + dIp;
    }

    public static String getCatKey() {
        return REDIS_KEY_PREFIX + REDIS_KEY_SEPARATOR + "cat";
    }

    public static String getJudgeGuide() {
        return REDIS_KEY_PREFIX + REDIS_KEY_SEPARATOR + REDIS_GUIDE;
    }

    public static String getJudgeGuideTab() {
        return REDIS_KEY_PREFIX + REDIS_KEY_SEPARATOR + REDIS_GUIDE_TAB;
    }

    public static String getRedisCheckTime() {
        return REDIS_KEY_PREFIX + REDIS_KEY_SEPARATOR + CHECK_TIME;
    }

    public static String getTibCaChe(String ip) {
        return REDIS_KEY_PREFIX + REDIS_KEY_SEPARATOR + TIB + REDIS_KEY_SEPARATOR + ip;
    }
}
