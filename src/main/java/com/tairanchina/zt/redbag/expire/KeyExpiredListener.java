package com.tairanchina.zt.redbag.expire;

import redis.clients.jedis.JedisPubSub;

public class KeyExpiredListener extends JedisPubSub {




    @Override
    public void onMessage(String channel, String message) {
        System.out.println("channel:" + channel + "receives message :" + message);
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        System.out.println("channel:" + channel + "receives expired message :" + message);

    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println("onPSubscribe " + pattern + " " + subscribedChannels);

    }

}