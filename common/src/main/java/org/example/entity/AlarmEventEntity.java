package org.example.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.example.entity.custom.deserializer.LocalDateTimeDeserializer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @author violet
 * @since 2022/12/27
 */
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlarmEventEntity {
    private String id;
    @JsonAlias("S_PORT")
    private Set<Integer> sourcePort;
    @JsonAlias("D_PORT")
    private Set<Integer> distinctPort;
    @JsonAlias("S_UNIT")
    private String unitName;
    @JsonAlias("K_C")
    private String attackChain;
    @JsonAlias("K_C_ID")
    private String attackChainId;
    @JsonAlias("CAT2")
    private String cat2;
    @JsonAlias("REQ_BODY")
    private String requestBody;
    @JsonAlias("R_ID")
    private String ruleId;
    @JsonAlias("R_NAME")
    private String ruleName;
    @JsonAlias("S_LATI")
    private String sourceLatitude;
    @JsonAlias("CAT2_ID")
    private String cat2Id;
    @JsonAlias("D_IP")
    private Set<String> destinationIp;
    @JsonAlias("CUR_TIME")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "GMT+8")
    private LocalDateTime currentTime;
    @JsonAlias("CAT1")
    private String cat1;
    @JsonAlias("S_COUNTRY")
    private String sourceCountry;
    @JsonAlias("S_CITY")
    private String sourceCity;
    @JsonAlias("EVT_T")
    private String eventType;
    @JsonAlias("D_BACT")
    private String destinationBact;
    @JsonAlias("REQ_UA")
    private String requestUa;
    @JsonAlias("PARAM")
    private String requestParams;
    @JsonAlias("SEVERITY")
    private String severity;
    @JsonAlias("TAGS")
    private Set<String> tags;
    @JsonAlias("DOMAIN")
    private String domain;
    @JsonAlias("REQ_STATUS")
    private String requestStatus;
    @JsonAlias("URL")
    private String url;
    @JsonAlias("DVC_T")
    private String deviceType;
    @JsonAlias("SESSION_ID")
    private String sessionId;
    @JsonAlias("REQ_COOKIES")
    private String requestCookie;
    @JsonAlias("D_MAC")
    private String destinationMac;
    @JsonAlias("S_PROVINCE")
    private String sourceProvince;
    @JsonAlias("AGT_A")
    private String agentIp;
    @JsonAlias("RECV_TIME")
    private String receiveTime;
    @JsonAlias("DESC")
    private String eventDescription;
    @JsonAlias("DVC_A")
    private String deviceIp;
    @JsonAlias("CO_ID_LIST")
    private Set<String> relationId;
    @JsonAlias("D_LATI")
    private String destinationLatitude;
    @JsonAlias("NAME")
    private String name;
    @JsonAlias("PRO_T")
    private String protocolTransport;
    @JsonAlias("S_IP")
    private Set<String> sourceIp;
    @JsonAlias("RECORDER")
    private String recorder;
    @JsonAlias("REQ_METHOD")
    private String requestMethod;
    @JsonAlias("SOLU")
    private String solution;
    @JsonAlias("DVC_ID")
    private String deviceId;
    @JsonAlias("D_COUNTRY")
    private String destinationCountry;
    @JsonAlias("CUSTOM5")
    private String custom5;
    @JsonAlias("D_CITY")
    private String destinationCity;
    @JsonAlias("S_LONG")
    private String sourceLongitude;
    @JsonAlias("TIME")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "GMT+8")
    private LocalDateTime time;
    @JsonAlias("S_DISTRICT")
    private String sourceDistrict;
    @JsonAlias("D_DISTRICT")
    private String destinationDistrict;
    @JsonAlias("D_LONG")
    private String destinationLongitude;
    @JsonAlias("alarm_indexing_id")
    private String alarmIndexingId;
    @JsonAlias("AGT_ID")
    private String agtId;
    @JsonAlias("VENDOR")
    private String vendor;
    @JsonAlias("SEVERITY_ID")
    private String severityId;
    @JsonAlias("CAT1_ID")
    private String cat1Id;
    @JsonAlias("THREATSCORE")
    private String threatScore;
    @JsonAlias("CUSTOM4")
    private String custom4;
    @JsonAlias("S_SEC_DOM")
    private String sourceSecurityDomain;
    @JsonAlias("D_SEC_DOM")
    private String destinationSecurityDomain;
    @JsonAlias("S_MAC")
    private String sourceMac;
    @JsonAlias("S_BCAT")
    private String sourceBcat;
    @JsonAlias("D_BCAT")
    private String destinationBcat;
    @JsonAlias("D_PORT")
    private List<String> destinationport;
    @JsonAlias("D_PROVINCE")
    private String destinationProvince;
    @JsonAlias("STATUS")
    private String status;

}
