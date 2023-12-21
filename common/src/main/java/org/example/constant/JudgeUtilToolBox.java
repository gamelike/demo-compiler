package org.example.constant;

import org.example.pojo.vo.JudgeToolBoxVo;

/**
 * @author violet
 * @since 2022/12/28
 */
public enum JudgeUtilToolBox {
    alarmIndexList("告警列表", "分析展示安全事件归并前的告警列表，根据原始告警信息辅助研判与分析。", true, true, 1),
    relationAsset("关联资产", "分析展示安全事件关联资产的线索特征，展示资产的名称、设备类型、部署位置等信息。", true, true, 2),
    eventSource("事件源", "分析展示安全事件源的线索特征，包括资产的通连关系、异常协议流量分布及活跃度分布等。", true, false, 3),
    eventDestination("事件目的", "分析展示安全事件目的的线索特征，包括资产的通连关系、异常协议流量分布及活跃度分布等。", true, false, 4),
    trafficAnalysis("流量分析", "分析展示安全事件流量线索特征,包括流量时间分布、活跃度、目地IP、目地端口等。", true, false, 5),
    tib("威胁情报", "分析展示安全事件命中威胁情报的线索特征，威胁情报包括IP地址、URL地址、域名、邮箱地址及文件HASH等。", true, true, 6),
    whiteList("白名单", "分析展示安全事件命中白名单的线索证据，白名单支持事件源、事件目的、事件名称及时间范围进行组合。", true, true, 7),
    clue("异常线索现象", "分析展示安全事件命中的异常线索现象，异常线索现象为人工上报的分为异常线索和异常线索现象。", true, true, 8),
    vulnerability("漏洞分析", "分析展示安全事件关联资产命中的漏洞信息，根据漏洞利用分析潜在的攻击威胁。", true, true, 9),
    pcap("pcap", "分析展示安全事件Pcap包的线索特征，匹配网络攻击监测分析软件流量事件，按需获取Pcap包，根据会话解析数据包并展示线索。", true, true, 10),
    httpProtocolAnalysis("http协议分析", "分析展示安全事件Pcap包中的HTTP协议数据线索数据。", true, true, 11),
    ftpProtocolAnalysis("ftp协议分析", "分析展示安全事件Pcap包中的FTP协议数据线索数据。", false, true, 12),
    terminalProcessAnalysis("终端进程分析", "分析展示安全事件产生终端上的行为列表，展示终端进程运行状态。", true, true, 13),
    terminalFileAnalysis("终端文件分析", "分析展示安全事件产生终端上的行为列表，展示终端进程运行状态。", true, true, 14),
    exceptionEvent("异常事件", "分析展示安全事件关联的历史异常事件列表，根据历史的异常事件特征挖掘线索。", true, true, 15),
    endogenousEvent("内生事件", "分析展示安全事件关联的通过模型分析安全事件相关历史日志数据后发现的内生事件，根据历史的内生事件挖掘线索。", true, true, 16),
    otherClue("其他线索", "分析展示人工上传的其他与安全事件相关的线索。", true, true, 17);
    private final JudgeToolBoxVo judgeToolBoxVo;

    public JudgeToolBoxVo getJudgeToolBoxVo() {
        return judgeToolBoxVo;
    }

    JudgeUtilToolBox(String name, String description, boolean visible, boolean isStatistic, int order) {
        judgeToolBoxVo = new JudgeToolBoxVo(this.name(), name, description, order, visible, isStatistic);
    }

}
