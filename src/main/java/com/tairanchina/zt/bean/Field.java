package com.tairanchina.zt.bean;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class Field implements Serializable {
    private String fieldName;
    private String fieldType;
    private String defaultValue;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
