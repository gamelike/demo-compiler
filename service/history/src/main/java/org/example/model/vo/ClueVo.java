package org.example.model.vo;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * @author violet
 * @since 2023/4/26
 */
@Getter
@SuperBuilder
public class ClueVo extends BaseVo {
    private String relationId;
    private String process;
    private String type;
    private double score;
    private String result;
}
