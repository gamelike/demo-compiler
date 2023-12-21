package org.example.constant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class EsField {

    // 字段
    @Value("${ID}")
    private String ID;

    @Value("${ALARM_NAME}")
    private String ALARM_NAME;

    @Value("${ALARM_EVENT_TYPE}")
    private String ALARM_EVENT_TYPE;

    @Value("${ALARM_SEVERITY}")
    private String ALARM_SEVERITY;

    @Value("${ALARM_S_IP}")
    private String ALARM_S_IP;

    @Value("${ALARM_S_IP_KEYWORD}")
    private String ALARM_S_IP_KEYWORD;

    @Value("${ALARM_D_IP}")
    private String ALARM_D_IP;

    @Value("${ALARM_D_IP_KEYWORD}")
    private String ALARM_D_IP_KEYWORD;

    @Value("${START_TIME}")
    private String START_TIME;

    @Value("${ALARM_TYPE}")
    private String ALARM_TYPE;

    @Value("${ALARM_TOTAL}")
    private String ALARM_TOTAL;

    @Value("${TRIGGER_ID}")
    private String TRIGGER_ID;

    @Value("${TRIGGER_NAME}")
    private String TRIGGER_NAME;

    @Value("${DISPOSAL_STATUS}")
    private String DISPOSAL_STATUS;

    @Value("${ALARM_INDEXING_ID}")
    private String ALARM_INDEXING_ID;

    @Value("${TIME}")
    private String TIME;

    @Value("${S_IP}")
    private String S_IP;

    @Value("${D_IP}")
    private String D_IP;

    @Value("${NAME_KEYWORD}")
    private String NAME_KEYWORD;

    @Value("${NAME}")
    private String NAME;

    @Value("${S_ASSCAT}")
    private String S_ASSCAT;

    @Value("${D_ASSCAT}")
    private String D_ASSCAT;

    @Value("${S_UNIT}")
    private String S_UNIT;

    @Value("${D_UNIT}")
    private String D_UNIT;

    @Value("${D_PORT}")
    private String D_PORT;

    @Value("${S_PORT}")
    private String S_PORT;

    @Value("${CAT1}")
    private String CAT1;

    @Value("${D_SCOPE}")
    private String D_SCOPE;

    @Value("${REVC_TIME}")
    private String REVC_TIME;

    @Value("${REQ_METHOD}")
    private String REQ_METHOD;

    @Value("${URL}")
    private String URL = "URL";

    @Value("${RES_STATUS}")
    private String RES_STATUS;

    @Value("${SEVERITY}")
    private String SEVERITY;

    @Value("${STATUS}")
    private String STATUS;

    @Value("${PRO_A}")
    private String PRO_A;

    @Value("${FLE_N}")
    private String FLE_N;

    @Value("${FLE_P}")
    private String FLE_P;

    @Value("${FLE_T}")
    private String FLE_T;

    @Value("${FLE_DIR}")
    private String FLE_DIR;

    @Value("${FLE_H}")
    private String FLE_H;

    @Value("${CO_ID_LIST}")
    private String CO_ID_LIST;

    @Value("${REQ_COOKIES}")
    private String REQ_COOKIES;

    @Value("${REQ_BODY}")
    private String REQ_BODY;

    @Value("${RES_BODY}")
    private String RES_BODY;

    @Value("${REQ_UA}")
    private String REQ_UA;

    @Value("${CAT2}")
    private String CAT2;

    @Value("${ALARM_S_IP_COUNT}")
    private String ALARM_S_IP_COUNT;

    @Value("${ALARM_D_IP_COUNT}")
    private String ALARM_D_IP_COUNT;

    @Value("${ALARM_S_SCOPE_COUNT}")
    private String ALARM_S_SCOPE_COUNT;

    @Value("${ALARM_D_SCOPE_COUNT}")
    private String ALARM_D_SCOPE_COUNT;

    @Value("${RAW}")
    private String RAW;

    @Value("${R_ID}")
    private String R_ID;

    @Value("${DVC_T}")
    private String DVC_T;

    @Value("${DVC_A}")
    private String DVC_A;

    @Value("${K_C}")
    private String K_C;

    @Value("${RESULT}")
    private String RESULT;

    @Value("${REASON}")
    private String REASON;

    @Value("${R_NAME}")
    private String R_NAME;

    @Value("${MSG}")
    private String MSG;

    @Value("${ALARM_EVENT_NAME}")
    private String ALARM_EVENT_NAME;

    @Value("${CONFIDENCE}")
    private String CONFIDENCE;

    @Value("${ATT_RESULT}")
    private String ATT_RESULT;

    @Value("${ALARM_INDEXING_STATUS}")
    private String ALARM_INDEXING_STATUS;

    @Value("${CUSTOM1}")
    private String CUSTOM1;

    @Value("${CUSTOM5}")
    private String CUSTOM5;

    @Value("${SUGGESTION}")
    private String SUGGESTION;

    @Value("${ATK_DET}")
    private String ATK_DET;

    @Value("${BYTES_T}")
    private String BYTES_T;

    @Value("${PCK_T}")
    private String PCK_T;

    @Value("${OP_T}")
    private String OP_T;

    @Value("${END_TIME}")
    private String END_TIME;

    @Value("${S_ASSID}")
    private String S_ASSID;

    @Value("${S_UID}")
    private String S_UID;

    @Value("${D_ASSID}")
    private String D_ASSID;

    @Value("${D_UID}")
    private String D_UID;

    @Value("${S_ISP}")
    private String S_ISP;

    @Value("${DVC_ID}")
    private String DVC_ID;

    @Value("${ALARM_SWITCH}")
    private String ALARM_SWITCH;

    @Value("${UPDATE_TIME}")
    private String UPDATE_TIME;

    @Value("${ALARM_S_SCOPE}")
    private String ALARM_S_SCOPE;

    @Value("${ALARM_D_SCOPE}")
    private String ALARM_D_SCOPE;

    @Value("${ALARM_DVC_T}")
    private String ALARM_DVC_T;

    @Value("${INFLUENCE_TYPE}")
    private String INFLUENCE_TYPE;

    @Value("${S_IPV6}")
    private String S_IPV6;

    @Value("${D_IPV6}")
    private String D_IPV6;
    @Value("${E_ID:E_ID}")
    private String E_ID;

    @Value("${ALARM_S_ASSN:ALARM_S_ASSN}")
    private String ALARM_S_ASSN;
    @Value("${ALARM_D_ASSN:ALARM_D_ASSN}")
    private String ALARM_D_ASSN;

    @Value("${CREATE_TASK:CREATE_TASK}")
    private String CREATE_TASK;

    @Value("${RECORDER}")
    private String RECORDER;

    @Value("${PRO_T:PRO_T}")
    private String PRO_T;

    @Value("${MONITOR_IP:MONITOR_IP}")
    private String MONITOR_IP;

    @Value("${CREATE_TIME:CREATE_TIME}")
    private String CREATE_TIME;

    @Value("${PRS_NE:PRS_NE}")
    private String PRS_NE;
}
