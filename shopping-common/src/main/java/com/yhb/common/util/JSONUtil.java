package com.yhb.common.util;

import com.alibaba.fastjson.*;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jiayu.shenjy on 2016/2/1.
 */
public class JSONUtil {
    private static final Logger logger = LoggerFactory.getLogger(JSONUtil.class);
    /**
     * 将对象转换成json字符串
     * @param object
     * @return
     */
    public static String toJsonString(Object object) {
        return object == null ? "" : JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect);
    }

    public static String toJsonString(Object object, SerializerFeature...features) {

        return object == null ? "" : JSON.toJSONString(object, features);
    }

    private static ValueFilter filter = new ValueFilter() {
        @Override
        public Object process(Object obj, String s, Object v) {
            if (v == null){
                return "";
            }
            return v;
        }
    };

    public static String toJsonStringWithNull(Object object){

        return object == null ? "" : JSON.toJSONString(object,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullBooleanAsFalse);
    }



    /**
     * 将字符串转换成json对象
     * @param string
     * @return
     */
    public static JSONObject toJSONObj(String string) {
        return StringUtils.isBlank(string) ? null : JSON.parseObject(string);
    }

    public static JSONObject objToJSONObj(Object o) {
        return o == null ? null : JSON.parseObject(JSON.toJSONString(o));
    }
    /**
     * 将字符串转换成jsonArray对象
     * @param string
     * @return
     */
    public static JSONArray toJSONArray(String string) {
        return StringUtils.isBlank(string) ? null : JSON.parseArray(string);
    }
    /**
     * json串转换成pojo类
     * @param json
     * @param pojoClass
     * @param <T>
     * @return
     */
    public  static <T> T jsonToObject(String json, Class<T> pojoClass) {
        Object pojo;
        pojo = JSON.parseObject(json, pojoClass);
        return (T) pojo;
    }

    /**
     * json串转换成pojo类
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public  static <T> T jsonToObject(String json, TypeReference<T> type) {
        Object pojo;
        pojo = JSON.parseObject(json, type);
        return (T) pojo;
    }


    /**
     * wdklab 做数据传递时使用， 从一个jsonobject中根据path抽取部分值
     * @param sourceJson
     * @param jsonPath
     * @return
     */
    public static String extract(String sourceJson, String jsonPath){
        JSONObject jsonObject = JSON.parseObject(sourceJson);
        Object result = JSONPath.eval(jsonObject, jsonPath);
        if (result instanceof String){
            return result.toString();
        } else{
            return JSON.toJSONString(result);
        }

    }

    /**
     * 将新的值根据指定path注入原来的jsonobject
     * @param sourceJson
     * @param jsonPath
     * @param newValue
     * @param dataType
     * @return
     */
    public static String inject(String sourceJson, String jsonPath, String newValue, int dataType){
        Object sourceObject = JSON.parseObject(sourceJson);

        Object newValueObject;
        switch(dataType)
        {
            case 0:  // String
                newValueObject = newValue;
                break;
            case 1: // number
                newValueObject = Double.valueOf(newValue);
                break;
            case 2: // boolean
                newValueObject = Boolean.valueOf(newValue);
                break;
            case 3: //array
                newValueObject = JSON.parseArray(newValue);
                break;
            case 4: //object
                newValueObject = JSON.parseObject(newValue);
                break;
            default:
                newValueObject = newValue;
                break;
        }
        logger.warn("注入到当前节点的入参before:"+ sourceJson);
        logger.warn("注入到当前节点的入参jsonPathInput:"+ jsonPath );
        logger.warn("注入到当前节点的入参actualValue:"+ newValue );
        if (JSONPath.contains(sourceObject,jsonPath)){
            JSONPath.set(sourceObject, jsonPath, newValueObject);
        } else  {
            //throw new RuntimeException("inject value by path: "+jsonPath+" failed");
            logger.error("注入异常, path："+jsonPath+" failed");
        }
        logger.warn("注入到当前节点的入参after:"+ JSON.toJSONString(sourceObject) );

        return JSON.toJSONString(sourceObject);

    }

}
