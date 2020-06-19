package com.scottxuan.base.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * @author : scottxuan
 */
@Slf4j
public class JSONUtils {

    /**
     * Object转JsonString
     * @param object
     * @return
     */
    public static String toJsonString(Object object){
        if (ObjectUtils.isNotEmpty(object)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * jsonString 转 Object
     * @param jsonString
     * @param clazz
     * @return
     */
    public static Object parseObject(String jsonString, Class clazz){
        if (StringUtils.isNotEmpty(jsonString) && ObjectUtils.isNotEmpty(clazz)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(jsonString, clazz);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
