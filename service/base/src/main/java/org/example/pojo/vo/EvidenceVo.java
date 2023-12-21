package org.example.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author violet.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EvidenceVo {

    private String id;

    private Integer result;

    private String type;

    private String desc;

    public EvidenceVo() {
    }

    public EvidenceVo(String id, Integer result, String type, String desc) {
        this.id = id;
        this.result = result;
        this.type = type;
        this.desc = desc;
    }
}
