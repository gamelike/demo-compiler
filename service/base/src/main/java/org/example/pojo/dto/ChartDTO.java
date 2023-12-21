package org.example.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description: chart DTO.
 *
 * @author zhao_yifan
 * @date 2022/4/19
 */
@Data
public class ChartDTO implements Serializable {

  private Map<String, List<SubData>> dimensions;

  private void setDimensions(Map<String, List<SubData>> dimensions) {
    this.dimensions = dimensions;
  }

  public Map<String, List<SubData>> getDimensions() {
    if (dimensions == null) {
      setDimensions(new HashMap<>());
    }
    return dimensions;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class SubData implements Serializable {

    private String name;
    private String value;
  }
}
