package com.yjy.web.vo;

/**
 * Created by lwk on 2016/6/17.
 */
public class JedisVo {

    private String key;
    private String value;

    public JedisVo(String key) {
        this.key=key;
    }

    public JedisVo(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
