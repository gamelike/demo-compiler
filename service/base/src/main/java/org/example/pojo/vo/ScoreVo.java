package org.example.pojo.vo;

import lombok.Builder;
import lombok.Getter;

/**
 * @author violet
 * @since 2023/5/17
 */
@Builder
@Getter
public class ScoreVo {
    private String desc;
    private double score;
    private double confidence;
    private int count;
}
