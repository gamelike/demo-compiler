package org.example.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.example.constant.EvidenceType;

/**
 * @author violet
 * @since 2023/4/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class HistoryClue {
    private String id;
    private double score;
    private EvidenceType type;
}
