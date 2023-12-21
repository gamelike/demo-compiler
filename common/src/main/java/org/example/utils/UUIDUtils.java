package org.example.utils;

import java.util.UUID;

/**
 * @author zhao_yifan
 * @since 2022/3/28
 */
public class UUIDUtils {
    /**
     * 生成32位的UUID
     *
     * @author zhao_yifan
     **/
    public static String get32UUID() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 32);
    }
}
