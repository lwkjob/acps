package com.yjy.common.utils;

/**
 * Created by dengs_000 on 2015/4/16.
 */
public class StringUtils
{
    /**
     * 判断字符串是否为空串。
     *
     * @param string 待判断的字符串
     * @return 是否为空串
     */
    public static boolean isEmptyString(String string)
    {
        return ((string == null) || (string.isEmpty()));
    }
}
