package org.example.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * description: 列表展示VO.
 *
 * @author zhao_yifan
 * @date 2022/4/22
 */
@Data
public class ListVO<T> {

  private List<T> list;
  private Long total;

}
