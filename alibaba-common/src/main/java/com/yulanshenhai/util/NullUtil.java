package com.yulanshenhai.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author JoeZhou
 */
public class NullUtil {

    public static boolean isNull(Object param){
        return param == null;
    }

    public static boolean isNotNull(Object param){
        return param != null;
    }

    public static boolean isBlank(String param){
        return StringUtils.isBlank(param);
    }

    public static boolean isNotBlank(String param){
        return !StringUtils.isBlank(param);
    }

    public static boolean hasBlank(String... params) {
        for (String param : params) {
            if (StringUtils.isBlank(param)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasNull(Object... params) {
        for (Object param : params) {
            if (param == null) {
                return true;
            }
        }
        return false;
    }
}

