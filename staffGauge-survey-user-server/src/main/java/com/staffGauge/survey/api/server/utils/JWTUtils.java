package com.staffGauge.survey.api.server.utils;


import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mr.F on 2020/2/1
 */
public class JWTUtils {
    /**
     * 密钥
     */
    private static final String SECRET = "fu_test";
    /**
     * 默认字段key:exp
     */
    private static final String EXP = "exp";
    /**
     * 默认字段key:payload
     */
    private static final String PAYLOAD = "payload";

    /**
     * 加密
     *
     * @param object  加密数据
     * @param maxTime 有效期（毫秒数）
     * @param <T>
     * @return
     */
    public static <T> String encode(T object, long maxTime) {
        try {
            final JWTSigner signer = new JWTSigner(SECRET);
            final Map<String, Object> data = new HashMap<>(10);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(object);
            data.put(PAYLOAD, jsonString);
            data.put(EXP, System.currentTimeMillis() + maxTime);
            return signer.sign(data);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 数据解密
     *
     * @param jwt    解密数据
     * @param tClass 解密类型
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T decode(String jwt, Class<T> tClass) throws Exception {
        final JWTVerifier jwtVerifier = new JWTVerifier(SECRET);
        final Map<String, Object> data = jwtVerifier.verify(jwt);
        //判断数据是否超时或者符合标准
        if (data.containsKey(EXP) && data.containsKey(PAYLOAD)) {
            long exp = (long) data.get(EXP);
            long currentTimeMillis = System.currentTimeMillis();
            if (exp > currentTimeMillis) {
                String json = (String) data.get(PAYLOAD);
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(json, tClass);
            } else {
                throw new Exception("用户登录超时");
            }
        } else {
            throw new Exception("用户token错误");
        }
    }

}
