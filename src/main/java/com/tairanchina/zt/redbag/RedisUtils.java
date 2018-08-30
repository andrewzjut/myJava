package com.tairanchina.zt.redbag;

import com.alibaba.fastjson.JSONObject;
import com.tairanchina.zt.bean.Field;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.*;

public class RedisUtils {

    private static JedisPool pool = null;
    private static Map<String, String> pro = null;

    public synchronized static void init(Map<String, String> props) {
        pro = props;
        if (pool == null) {
            pool = getPool(props);
        }
    }

    private static JedisPool getPool(Map<String, String> props) {
        if (pool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(500);
            config.setMaxIdle(5);
            config.setMaxWaitMillis(1000 * 10);
            // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);
            // GenericObjectPoolConfig config = new GenericObjectPoolConfig();
            pool = new JedisPool(config, "localhost",
                    6379,
                    2000
            );
        }
        return pool;
    }

    public static Jedis getResource() {
        if (pool == null) {
            pool = getPool(pro);
        }
        return pool.getResource();
    }


    public static Map<String, String> getConfig() {
        Jedis jedis = null;
        Map<String, String> kv = new HashMap<>();
        try {
            jedis = getResource();
            Set<String> keySet = jedis.keys("FieldConfig*");
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                kv.put(key, jedis.get(key));
            }
        } catch (Exception e) {
            System.out.println("failed to connect redis.");
        } finally {
            jedis.close();
        }
        return kv;
    }

    public static void main(String[] args) {
        RedisUtils.init(null);
        Jedis jedis = getResource();
        Map<String,String> map = jedis.hgetAll("OFFSET:"+"fd-test-kdqb-format10");
        System.out.println(map);
//        Jedis jedis = getResource();
//        Map<String, String> map = new HashMap<>();
//        map.put("appDevice", "X-Device-Id;X-Auth;X-IP;CurrentTime");
//        map.put("appEvent", "X-Device-Id;App-Id;Channel-Id;App-Version;X-User-Id;Network;Location;X-Auth;X-IP;CurrentTime");
//        map.put("h5Device", "X-Device-Id;X-Auth;X-IP;CurrentTime");
//        map.put("h5Event", "X-Device-Id;X-Source-Url;X-Current-Url;X-User-Id;X-Auth;X-IP;CurrentTime");
//
//        String key = "FieldConfig:";
//        for (String s : map.keySet()) {
//            String[] values = map.get(s).split(";");
//            List<Field> fieldList = new ArrayList<>();
//            for (int i = 0; i < values.length; i++) {
//                Field field = new Field();
//                field.setFieldName(values[i]);
//                if (values[i].equals("CurrentTime")) {
//                    field.setFieldType("Long");
//                } else {
//                    field.setFieldType("String");
//                }
//                field.setDefaultValue("");
//                fieldList.add(field);
//                jedis.set(key + s, JSONObject.toJSONString(fieldList));
//            }
//        }
//
//
//        Map<String,String> list = RedisUtils.getConfig();
//        System.out.println(JSONObject.toJSONString(list));
    }

}
