package com.yjy.apcs.rpc.server.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/7/6.
 */
public class ReflectTools {

    private static Logger logger = LoggerFactory.getLogger(ReflectTools.class);

    public static synchronized void copyBean(Object dest, Object orig) throws Exception {
        Field[] fields = orig.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object o = field.get(orig);
            String fieldName = field.getName();
            String fileTypeStr = field.getType().getName();
            if (o == null || fieldName.equals("serialVersionUID")) {
                continue;
            }

            Object value = null;
            BigDecimal bigDecimal = null;
            if (fileTypeStr.equals("java.math.BigDecimal")) {
                bigDecimal = (BigDecimal) o;
                value = bigDecimal.doubleValue();
            } else {
                value = o;
            }
            Field destField = dest.getClass().getDeclaredField(fieldName);
            String setMethonName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            destField.setAccessible(true);
            Method setMethon = dest.getClass().getMethod(setMethonName, destField.getType());
            setMethon.invoke(dest, value);
            destField.setAccessible(true);
        }
    }
}

