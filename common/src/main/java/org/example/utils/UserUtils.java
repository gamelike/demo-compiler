package org.example.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author violet.
 */
public class UserUtils {

    public static String getUserName() {
        StringBuilder user = new StringBuilder();
        if (RequestContextHolder.getRequestAttributes() != null) {
            String principal = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest().getHeader("principal");
            if (StringUtils.isNotBlank(principal)) user.append(principal);
        }
        return StringUtils.isBlank(user.toString()) ? "匿名用户" : user.toString();
    }

    public static String getUserNameByUrl() {
        StringBuilder user = new StringBuilder();
        if (RequestContextHolder.getRequestAttributes() != null) {
            String principal = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest().getParameter("principal");
            if (StringUtils.isNotBlank(principal)) user.append(principal);
        }
        return StringUtils.isBlank(user.toString()) ? "匿名用户" : user.toString();
    }

}
