package org.example.pojo.vo;

import lombok.Data;

import java.util.Set;

/**
 * @author zhao_yifan
 **/
@Data
public class SourceEventVo {

  private Set<String> sIp;
  private String eventName;
  private String assetName;
  private String unit;
  private String exceptionBehavior;
  private String username;
  private String assetId;
  private String eventType;
}
