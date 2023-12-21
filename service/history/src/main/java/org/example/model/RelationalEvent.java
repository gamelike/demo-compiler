package org.example.model;

import lombok.Data;
import org.example.constant.EvidenceType;
import org.example.exception.ValueObjectCreatedException;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author violet
 * @since 2023/4/26
 */
@Data
public class RelationalEvent {
    private final String id;
    private final String keyMessage;
    private final LocalDateTime eventTime;
    private final EvidenceType evidenceType;

    public RelationalEvent(String id, @Nullable String keyMessage, LocalDateTime eventTime, EvidenceType evidenceType) throws ValueObjectCreatedException {
        this.id = id;
        this.keyMessage = keyMessage;
        this.eventTime = eventTime;
        this.evidenceType = evidenceType;
        validNpe();
    }

    private void validNpe() throws ValueObjectCreatedException {
        if (Objects.isNull(id)) {
            throw new ValueObjectCreatedException("创建历史关联实体失败, 事件ID为空");
        }
        if (Objects.isNull(eventTime)) {
            throw new ValueObjectCreatedException("创建历史关联实体失败，事件时间为空");
        }
        if (Objects.isNull(evidenceType)) {
            throw new ValueObjectCreatedException("创建历史关联实体失败，证据类型为空");
        }
    }
}
