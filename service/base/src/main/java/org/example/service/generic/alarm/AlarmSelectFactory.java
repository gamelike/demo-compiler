package org.example.service.generic.alarm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 创建策略工厂。
 *
 * @author violet
 * @since 2022/11/4
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AlarmSelectFactory {

    private final Map<String, AlarmSelect> map;

    /**
     * create {@link AlarmSelect} impl by {@link AlarmSelectType}
     *
     * @param type {@link AlarmSelectType}
     * @return {@link AlarmSelect}
     */
    public AlarmSelect create(AlarmSelectType type) {
        return map.get(type.name());
    }

}
