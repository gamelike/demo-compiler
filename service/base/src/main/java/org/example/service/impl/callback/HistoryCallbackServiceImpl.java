package org.example.service.impl.callback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.aware.HistoryCallback;
import org.example.service.CoreService;
import org.springframework.stereotype.Service;

/**
 * @author violet
 * @since 2023/5/6
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class HistoryCallbackServiceImpl implements HistoryCallback {
    private final CoreService coreService;

    @Override
    public void operationAlarmEvent(String id) {
        coreService.markOperation(id);
    }
}
