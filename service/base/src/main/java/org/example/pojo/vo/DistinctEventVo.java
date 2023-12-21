package org.example.pojo.vo;

import lombok.Data;

import java.util.Set;

/**
 * @author zhao_yifan
 **/
@Data
public class DistinctEventVo {

  private Set<String> dIp;
  private String eventName;
  private int port;
  private String assetName;
  private String unit;
  private String exceptionBehavior;
  private String username;
  private String assetId;
  private String eventType;
}
