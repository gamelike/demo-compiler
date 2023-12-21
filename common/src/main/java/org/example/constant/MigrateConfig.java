package org.example.constant;

/**
 * 索引迁移配置类
 * @param
 * @return
 * @throws
 * @author zhaoxu
 */
public class MigrateConfig {

    public static final String MIGRATE_INDEX = EsIndex.PREFIX + "public_alarm_index";

    public static final String EVENT_LIST = "EVENT_LIST";

    public static final String INTELLIGENCE_LIST = "INTELLIGENCE_LIST";

    public static final String TRACK_EVENT_LIST = "TRACK_EVENT_LIST";

    public static final String ID_IN_INTELLIGENCE_CONDITION = "id";

    public static final String FROM_TIME_IN_INTELLIGENCE_CONDITION = "fromTime";

    public static final String TO_TIME_IN_INTELLIGENCE_CONDITION = "toTime";

    public static final String ID_IN_ALARM_INDEX = "ID";

    public static final String CREATE_TIME_IN_ALARM_INDEX = "CREATE_TIME";

    public static final String EVENT_TIME = "EVENT_TIME";

}
