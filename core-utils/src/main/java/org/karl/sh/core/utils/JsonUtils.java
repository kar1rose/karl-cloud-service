package org.karl.sh.core.utils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * json工具转换类
 * 支持fastjson jackson
 *
 * @author ROSE
 * @date 2019/12/28 13:30
 **/
@Slf4j
public class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String convert(Object data) {
        return JSON.toJSONString(data);
    }

    public static <T> T parse(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    public static <T> T parseWithJackson(String json, Class<T> clazz) throws IOException {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("jackson 字符串转json失败：{}", e.getMessage());
            return null;
        }
    }

    public static String convertWithJackson(Object data) {
        try {
            return OBJECT_MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            log.error("jackson json转字符串失败：{}", e.getMessage());
            return null;
        }
    }
}
