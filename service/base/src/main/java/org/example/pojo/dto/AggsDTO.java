package org.example.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * description: es standard aggregations.
 *
 * @author zhao_yifan
 * @date 2022/4/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AggsDTO {

    private String key;
    private String doc_count = "0";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AggsDTO aggsDTO = (AggsDTO) o;
        return Objects.equals(key, aggsDTO.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
