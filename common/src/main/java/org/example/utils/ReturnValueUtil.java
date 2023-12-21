package org.example.utils;

import java.util.*;

@SuppressWarnings({"all"})
public class ReturnValueUtil {
    private final static String highlight_start = "<span class=\"hitText\">";
    private final static String highlight_end = "</span>";

    public ReturnValueUtil() {
    }

    public static void procValueHtmlEncode(Map<String, Object> rowMap, Map<String, Object> highlightMap) {
        Iterator var2 = rowMap.keySet().iterator();

        while(true) {
            String key;
            while(var2.hasNext()) {
                key = (String)var2.next();
                Object value = rowMap.get(key);
                if (value instanceof HashMap) {
                    Map<String, Object> childMap = new HashMap();
                    Map<String, Object> innerValue = (HashMap)value;
                    Iterator var7 = innerValue.keySet().iterator();

                    while(true) {
                        while(var7.hasNext()) {
                            String innerKey = (String)var7.next();
                            Object innervalue = innerValue.get(innerKey);
                            if (innervalue != null && innervalue instanceof ArrayList) {
                                ArrayList newvalue = new ArrayList();
                                ArrayList values = (ArrayList)innervalue;
                                Iterator var12 = values.iterator();

                                while(true) {
                                    while(var12.hasNext()) {
                                        Object obj = var12.next();
                                        if (!(obj instanceof Integer) && !(obj instanceof Long) && !(obj instanceof Double)) {
                                            newvalue.add(htmlEscape(obj.toString()));
                                        } else {
                                            newvalue.add(obj);
                                        }
                                    }

                                    childMap.put(innerKey, newvalue);
                                    break;
                                }
                            } else if (innervalue != null) {
                                childMap.put(innerKey, htmlEscape(innervalue.toString()));
                            }
                        }

                        rowMap.put(key, childMap);
                        break;
                    }
                } else if (value instanceof String) {
                    rowMap.put(key, htmlEscape(value.toString()));
                }
            }

            var2 = highlightMap.keySet().iterator();

            while(var2.hasNext()) {
                key = (String)var2.next();
                if (key.contains(".")) {
                    String[] dataStrutsKey = key.split("\\.");
                    String parentKey = dataStrutsKey[0];
                    String childKey = dataStrutsKey[1];
                    Object rowValue = rowMap.get(parentKey);
                    if (rowValue instanceof HashMap) {
                        Map dataStrutsMap = (Map)rowValue;
                        dataStrutsMap.put(childKey, highlightMap.get(key));
                    }
                } else {
                    rowMap.put(key, highlightMap.get(key));
                }
            }

            return;
        }
    }

    private static String getHighlightValue(String highLightValue) {
        String originalValue = highLightValue;
        int start = highLightValue.indexOf(highlight_start);
        int end = highLightValue.indexOf(highlight_end);
        if (start >= 0 && end > 0) {
            originalValue = highLightValue.substring(start + 22, end);
        }

        return originalValue;
    }

    public static Map<String, Object> procHighlightValue(Map<String, Object> rowMap, Map<String, String> HighlightFieldMap) {
        String highlightFieldValue = "";
        Map<String, Object> retHighlightMap = new HashMap();
        Iterator var4 = HighlightFieldMap.keySet().iterator();

        while(var4.hasNext()) {
            String key = (String)var4.next();
            highlightFieldValue = (String)HighlightFieldMap.get(key);
            if (key.contains(".keyword")) {
                key = key.split("\\.keyword")[0];
            }

            Object originalValue = rowMap.get(key);
            if (originalValue != null) {
                procHighlightValue(rowMap, key, highlightFieldValue, retHighlightMap);
            } else if (key.contains(".")) {
                String parentKey = key.split("\\.")[0];
                originalValue = rowMap.get(parentKey);
                if (originalValue != null) {
                    procHighlightValue(rowMap, key, highlightFieldValue, retHighlightMap);
                }
            }
        }

        return retHighlightMap;
    }

    private static void procHighlightValue(Map<String, Object> rowMap, String rowMapKey, String highlightFieldValue, Map<String, Object> retHighlightMap) {
        Object originalValue = rowMap.get(rowMapKey);
        String highTransValue;
        String highvalue;
        if (originalValue == null) {
            String[] dataStrutsKey = rowMapKey.split("\\.");
            highTransValue = dataStrutsKey[0];
            highvalue = dataStrutsKey[1];
            originalValue = rowMap.get(highTransValue);
        }

        if (originalValue instanceof ArrayList) {
            List originalValueList = (List)originalValue;
            Object[] newValue = new Object[originalValueList.size()];
            highvalue = getHighlightValue(highlightFieldValue);

            for(int arrayIndex = 0; arrayIndex < originalValueList.size(); ++arrayIndex) {
                String arrayValue = originalValueList.get(arrayIndex).toString();
                if (highlightFieldValue.contains(arrayValue)) {
                    String ret = htmlEscape(arrayValue);
                    ret = "<span class=\"hitText\">" + ret + "</span>";
                    newValue[arrayIndex] = ret;
                } else {
                    newValue[arrayIndex] = htmlEscape(arrayValue);
                }
            }

            retHighlightMap.put(rowMapKey, newValue);
        } else {
            String ret;
            if (!DigestUtils.isIP(originalValue.toString()) && !DigestUtils.isNumeric(originalValue.toString())) {
                highlightFieldValue = highlightFieldValue.substring(1, highlightFieldValue.length() - 1);
                if (!originalValue.toString().contains(highlightFieldValue)) {
                    return;
                }

                ret = htmlEscape(originalValue.toString());
                highTransValue = htmlEscape(highlightFieldValue);
                ret = ret.replace(highTransValue, "<span class=\"hitText\">" + highTransValue + "</span>");
                retHighlightMap.put(rowMapKey, ret);
            } else if (highlightFieldValue.contains(originalValue.toString())) {
                ret = htmlEscape(originalValue.toString());
                ret = "<span class=\"hitText\">" + ret + "</span>";
                retHighlightMap.put(rowMapKey, ret);
            } else {
                retHighlightMap.put(rowMapKey, originalValue);
            }
        }

    }

    private static String procHighlight(String str1, String str2) {
        if (StringUtil.isEmpty(str1)) {
            return "";
        } else if (!str1.substring(0, 1).equals(str2.substring(0, 1)) && !str1.startsWith("<span class=\"hitText\">")) {
            return procHightLight2(str1, str2);
        } else {
            StringBuilder html = new StringBuilder("");
            int str2_index = 0;

            for(int i = 0; i < str1.length(); ++i) {
                char c1 = str1.charAt(i);
                char c2 = 0;
                if (str2_index < str2.length()) {
                    c2 = str2.charAt(str2_index);
                }

                if (c1 != c2) {
                    html.append(c1);
                } else {
                    ++str2_index;
                    html.append(htmlEscape(c1));
                }
            }

            return html.toString();
        }
    }

    public static String procHightLight2(String str1, String str2) {
        String hightStart = "<span class=\"hitText\">";
        String hightEnd = "</span>";
        StringBuilder strTmp = new StringBuilder("");

        for(int i = 0; i < str2.length(); ++i) {
            char c1 = str2.charAt(i);
            strTmp.append(htmlEscape(c1));
        }

        str2 = strTmp.toString();
        StringBuilder strTmp2 = new StringBuilder("");

        String s2;
        for(int i = 0; i < str1.length(); ++i) {
            char c1 = str1.charAt(i);
            if (i + hightStart.length() < str1.length()) {
                s2 = str1.substring(i, i + hightStart.length());
                if (hightStart.equals(s2)) {
                    strTmp2.append(s2);
                    i = i + s2.length() - 1;
                    continue;
                }
            }

            if (i + hightEnd.length() < str1.length()) {
                s2 = str1.substring(i, i + hightEnd.length());
                if (hightEnd.equals(s2)) {
                    strTmp2.append(s2);
                    i = i + s2.length() - 1;
                    continue;
                }
            }

            strTmp2.append(htmlEscape(c1));
        }

        ArrayList list = new ArrayList();

        String start;
        while(str1.contains(hightStart) && str1.contains(hightStart)) {
            Map<String, String> map = new HashMap();
            s2 = str1.substring(0, str1.indexOf(hightStart));
            start = str1.substring(str1.indexOf(hightStart) + hightStart.length(), str1.indexOf(hightEnd));
            str1 = str1.substring(str1.indexOf(hightEnd) + hightEnd.length());
            map.put("start", s2);
            map.put("value", start);
            list.add(map);
        }

        for(int j = 0; j < list.size(); ++j) {
            Map<String, String> map = (Map)list.get(j);
            start = (String)map.get("start");
            String value = (String)map.get("value");

            for(int i = 0; i < str2.length(); ++i) {
                if (i + start.length() + value.length() < str2.length()) {
                    String s0 = str2.substring(0, i);
                    String s1 = str2.substring(i, i + start.length());
                    String s3 = str2.substring(i + start.length(), i + start.length() + value.length());
                    String s4 = str2.substring(i + start.length() + value.length(), str2.length());
                    if (start.equals(s1) && value.equals(s3)) {
                        str2 = s0 + start + hightStart + value + hightEnd + s4;
                        break;
                    }
                }
            }
        }

        return str2;
    }

    private static String htmlEscape(String str) {
        if (StringUtil.isEmpty(str)) {
            return "";
        } else {
            StringBuilder html = new StringBuilder("");

            for(int i = 0; i < str.length(); ++i) {
                char c = str.charAt(i);
                html.append(htmlEscape(c));
            }

            str = html.toString();
            if (str.contains(" \r\n ") || str.contains("\r\n")) {
                str = str.replace("\r\n", "&#092r&#092n");
            }

            if (str.contains(" \n ") || str.contains("\n")) {
                str = str.replace("\n", "&#092n");
            }

            if (str.contains(" \r ") || str.contains("\r")) {
                str = str.replace("\r", "&#092r");
            }

            return str;
        }
    }

    private static String htmlEscape(char c) {
        StringBuilder html = new StringBuilder("");
        switch(c) {
            case '"':
                html.append("&quot;");
                break;
            case '\'':
                html.append("&#039;");
                break;
            case '<':
                html.append("&lt;");
                break;
            case '>':
                html.append("&gt;");
                break;
            default:
                html.append(c);
        }

        return html.toString();
    }
}
