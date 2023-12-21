package org.example.aware;

/**
 * 服务方提供历史完成后进行的操作.
 *
 * @author violet
 * @since 2023/5/6
 */
public interface HistoryCallback {

    void operationAlarmEvent(String id);

}
