//package com.info.json;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JavaType;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.info.exception.LiftException;
//
//import java.io.IOException;
//import java.util.List;
//
//import static com.info.validator.Assert.assertTrue;
//
///**
// * 功能描述: 自定义响应结构
// *
// * @Params:  * @param null
// * @Author:  Gaosx 960889426@qq.com By User
// * @Date: 2019/6/13 20:10
// * @Return:
// */
//public class JsonUtils {
//
//    // 定义jackson对象
//    private static final ObjectMapper MAPPER = new ObjectMapper();
//
////    private static final ObjectMapper mapper = new ObjectMapper();
//
//    public static String obj2JsonStr(final Object obj) {
//        assertTrue(obj != null, "obj cannot be null");
//        try {
//            return MAPPER.writeValueAsString(obj);
//        } catch (JsonProcessingException e) {
//            throw new LiftException(e.getMessage());
//        }
//    }
//
//    public static <T> T jsonStr2Obj(final String jsonStr, final Class<T> clazz) {
//        assertTrue(jsonStr != null && !jsonStr.isEmpty(), "json string cannot be null or empty");
//        assertTrue(clazz != null, "target class cannot be null");
//        try {
//            return MAPPER.readValue(jsonStr, clazz);
//        } catch (IOException e) {
//            throw new LiftException(e.getMessage());
//        }
//    }
//
//    /**
//     * 将对象转换成json字符串。
//     * <p>Title: pojoToJson</p>
//     * <p>Description: </p>
//     *
//     * @param data
//     * @return
//     */
//    public static String objectToJson(Object data) {
//        try {
//            String string = MAPPER.writeValueAsString(data);
//            return string;
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 将json结果集转化为对象
//     *
//     * @param jsonData json数据
//     * @param :clazz   对象中的object类型
//     * @return
//     */
//    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
//        try {
//            T t = MAPPER.readValue(jsonData, beanType);
//            return t;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 将json数据转换成pojo对象list
//     * <p>Title: jsonToList</p>
//     * <p>Description: </p>
//     *
//     * @param jsonData
//     * @param beanType
//     * @return
//     */
//    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
//        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
//        try {
//            List<T> list = MAPPER.readValue(jsonData, javaType);
//            return list;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//}
