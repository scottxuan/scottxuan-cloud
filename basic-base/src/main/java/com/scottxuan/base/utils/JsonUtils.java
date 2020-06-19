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
public class JsonUtils {

    /**
     * Object转JsonString
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    public static String toJsonString(Object object) throws JsonProcessingException {
        if (ObjectUtils.isNotEmpty(object)) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(object);
        }
        return "";
    }

    /**
     * jsonString 转 Object
     * @param jsonString
     * @param clazz
     * @return
     * @throws IOException
     */
    public static Object parseObject(String jsonString, Class clazz) throws IOException {
        if (StringUtils.isNotEmpty(jsonString) && ObjectUtils.isNotEmpty(clazz)) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonString, clazz);
        }
        return null;
    }
}
