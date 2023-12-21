package org.example.pojo.vo;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * description:
 *
 * @author zhao_yifan
 * @date 2022/5/1
 */
@Data
public class SearchVo<T> {

  private String name;

  private List<T> value;

  public List<T> getValue() {
    if (this.value == null) {
      return this.value = Lists.newArrayList();
    } else {
      return this.value;
    }
  }

}
