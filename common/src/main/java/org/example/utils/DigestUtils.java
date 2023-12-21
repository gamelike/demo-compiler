package org.example.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DigestUtils {

    public static String md5Hex(String data)  {
        try {


            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(data.getBytes(StandardCharsets.UTF_8));
            byte[] digest = messageDigest.digest();
            StringBuilder result = new StringBuilder();
            for (byte per : digest) {
                result.append(Integer.toHexString((0x000000FF & per) | 0xFFFFFF00).substring(6));
            }
            return result.toString();
        }catch (Exception ignored){
        }
        return null;
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    public static boolean isIP(String addr) {
        if (addr.length() >= 7 && addr.length() <= 19 && !"".equals(addr)) {
            String rexp = "((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}(/8|/16|/24|/32)?";
            Pattern pat = Pattern.compile(rexp);
            Matcher mat = pat.matcher(addr);
            boolean matches = mat.matches();
            return matches;
        } else {
            return false;
        }
    }
}
