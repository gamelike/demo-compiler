package org.example.constant;

/**
 * es index.
 *
 * @author zhao_yifan
 */
public class EsIndex {

    public static final String PREFIX = "trx_wxyp_ods_";

    public static final String ALARM_INDEXING = PREFIX + "alarm_indexing*";
    public static final String ALARM_INDEXING_AUTO = PREFIX + "alarm_indexing";

    public static final String SECURITY_LOG = PREFIX + "security_log*";

    public static final String ALARM_EVENT = PREFIX + "alarm_event_*";

    public static final String HTTP_LOG = PREFIX + "http_log_*";

    public static final String MAIL_LOG = PREFIX + "mail_log_*";

    public static final String DNS_LOG = PREFIX + "dns_log_*";

    public static final String TELNET_LOG = PREFIX + "telnet_log_*";

    public static final String FTP_LOG = PREFIX + "ftp_log_*";

    public static final String NFS_LOG = PREFIX + "nfs_log_*";
    public static final String NETFLOW_LOG = PREFIX + "netflow_log_*";

    public static final String PERFORMANCE = PREFIX + "performance_*";

    public static final String SQL_LOG = PREFIX + "sql_log_*";

    public static final String JUDGE_HISTORY = PREFIX + "judge_history*";


    public static final String ALARM_INDEXING_MANUAL = PREFIX + "alarm_indexing_manual";

    public static final String ALARM_EVENT_AUTO = PREFIX + "alarm_event_2*";

    public static final String ALARM_EVENT_MANUAL = PREFIX + "alarm_event_manual";

    public static final String ALL = "*";

    public static final String OS_LOG = PREFIX + "os_log_*";
    public static final String PCAP = "t_trx_xyfx_jhsyp_send";

}
