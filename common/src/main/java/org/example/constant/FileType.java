package org.example.constant;

/**
 * description:
 *
 * @author zhao_yifan
 * @date 2022/4/28
 */
public enum FileType {
    INIT("init"),
    TEMPLATE("template"),
    RESOURCES("resources"),

    PCAP("pcap"),

    VULNERABILITY("vulnerability"),

    LOG("log"),

    EXCEPTION_EVENT("exception_event"),

    REPORT("report");

    private final String type;

    public String getType() {
        return type;
    }

    FileType(String type) {
        this.type = type;
    }
}
