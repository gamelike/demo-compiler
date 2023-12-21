package org.example.model.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.constant.EvidenceType;

/**
 * @author violet
 * @since 2023/5/9
 */
@Getter
@Builder
public class HistoryDto {
    private final String id;
    private final String description;
    private final EvidenceType evidenceType;
    private final String type;
    private final boolean marked;
    private final String keyFeature;
}
