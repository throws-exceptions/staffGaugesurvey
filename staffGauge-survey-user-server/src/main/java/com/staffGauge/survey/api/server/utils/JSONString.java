package com.staffGauge.survey.api.server.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mr.F on 2020/2/1
 */
public class JSONString {
    //    public static String getJSONString(int code, Map<String, String> res){
//        JSONObject json=new JSONObject();
//        json.put("code",code);
//        return json.toJSONString();
//    }
    public static Map<String, String> parseJson(String str) {
        JSONObject json = new JSONObject();
        Map map = json.parseObject(str, HashMap.class);
        return map;
    }

    public static String getJSONString(int code, String msg) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        return json.toJSONString();
    }

    public static String getJSONString(String key, String value) {
        JSONObject json = new JSONObject();
        json.put(key, value);
        return json.toJSONString();
    }

    public static String getJSONString(int code, Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            json.put(entry.getKey(), entry.getValue());
        }
        return json.toJSONString();
    }

    public static String getJSONMenuString(int id, String authName, String path) {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("authName", authName);
        json.put("path", path);
        return json.toJSONString();
    }


}
