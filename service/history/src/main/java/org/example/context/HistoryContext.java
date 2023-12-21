package org.example.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author violet
 * @since 2023/4/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class HistoryContext {
    private String id;
    private double initScore;
    private boolean hitWhite;
    private List<HistoryClue> historyClueList;

}
