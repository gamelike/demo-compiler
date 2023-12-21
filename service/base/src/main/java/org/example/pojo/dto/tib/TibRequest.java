package org.example.pojo.dto.tib;

import lombok.Builder;
import lombok.Getter;

/**
 * @author violet
 */
@Builder
@Getter
public class TibRequest {

    private String id;

    private String fromData;

    private String toData;

}
