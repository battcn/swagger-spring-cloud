package com.battcn.swagger.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 基于Jackson 封装的工具类
 *
 * @author Levin
 * @version 1.0.0
 * @since 2018/1/4 0004
 */
public class JacksonUtils {


    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JacksonUtils() {
    }


    /**
     * javaBean,list,array 转JSON字符串
     *
     * @param obj 对象
     * @return JSON串
     */
    public static String obj2json(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * JSON串转对象
     *
     * @param jsonStr JSON字符串
     * @param clazz   序列化的对象
     * @param <T>     泛型
     * @return 返回转换好的对象
     * @throws IOException 转换IO异常
     */
    public static <T> T json2pojo(String jsonStr, Class<T> clazz) throws IOException {
        return OBJECT_MAPPER.readValue(jsonStr, clazz);
    }

    /**
     * JSON串 转 JsonNode
     *
     * @param jsonStr JSON串
     * @return 转换结果
     */
    public static JsonNode json2node(String jsonStr) {
        try {
            return OBJECT_MAPPER.readTree(jsonStr);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * json string convert to map with javaBean
     *
     * @param jsonStr json string
     * @param clazz   map with javaBean
     * @param <T>     泛型
     * @return 转换结果
     * @throws IOException 转换异常
     */
    public static <T> Map<String, T> json2map(String jsonStr, Class<T> clazz)
            throws Exception {
        Map<String, Map<String, Object>> map = OBJECT_MAPPER.readValue(jsonStr,
                new TypeReference<Map<String, T>>() {
                });
        Map<String, T> result = Maps.newHashMap();
        for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            result.put(entry.getKey(), map2pojo(entry.getValue(), clazz));
        }
        return result;
    }

    /**
     * json array string 转 集合或 JAVA对象
     *
     * @param jsonArrayStr JSON串数组
     * @param clazz        序列化对象
     * @param <T>          泛型
     * @return 转换结果
     * @throws IOException 转换异常
     */
    public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz)
            throws IOException {
        List<Map<String, Object>> list = OBJECT_MAPPER.readValue(jsonArrayStr, new TypeReference<List<T>>() {
        });
        List<T> result = Lists.newArrayList();
        for (Map<String, Object> map : list) {
            result.add(map2pojo(map, clazz));
        }
        return result;
    }


    /**
     * Map对象转JAVA对象
     *
     * @param map   MAP
     * @param clazz 序列化对象
     * @param <T>   泛型
     * @return 转换结果
     */
    public static <T> T map2pojo(Map<?, ?> map, Class<T> clazz) {
        return OBJECT_MAPPER.convertValue(map, clazz);
    }


}
