package com.yjy.common.utils;

import java.util.Date;

/**
 * Created by yaobo on 2015/7/3.
 */
public class EntityUtils {
    public static Object autoSetEntityDate(Object entity){
        try {
            Object id = Reflections.getFieldValue(entity, "id");
            if (id == null){
                Reflections.setFieldValue(entity, "createDate", new Date());
            }
            Reflections.setFieldValue(entity, "updateDate", new Date());
        } catch (Exception e) {

        }
        return entity;
    }
}
