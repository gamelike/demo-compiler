package org.example.pojo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * description: alarm event dto.
 *
 * @author zhao_yifan
 * @date 2022/4/20
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlarmEventDTO implements Serializable {

  @JsonAlias("NAME")
  private String name;

  @JsonAlias("SEVERITY")
  private String severity;

  @JsonAlias("S_IP")
  private Set<String> sIp;

  @JsonAlias("D_IP")
  private Set<String> dIp;

  @JsonAlias("CAT2")
  private String classify;

  @JsonAlias("TIME")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "GMT+8")
  private LocalDateTime date;
}
