package org.example.constant;

/**
 * @author violet
 * @since 2023/5/23
 */
public enum JudgeType {
    automation("自动研判"),
    operation("人机协同研判");
    private final String display;

    JudgeType(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }
}
