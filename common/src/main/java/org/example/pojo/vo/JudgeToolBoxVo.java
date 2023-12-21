package org.example.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author violet
 * @since 2022/12/28
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
public class JudgeToolBoxVo {
    private String enName;
    private String cnName;
    private String description;
    private int order;
    private boolean visible;
    private boolean isStatistic;
}
