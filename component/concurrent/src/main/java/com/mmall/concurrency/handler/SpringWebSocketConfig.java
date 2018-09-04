//package com.mmall.concurrency.handler;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
////https://www.cnblogs.com/3dianpomian/p/5902084.html
//@Configuration
//@EnableWebSocket
//public class SpringWebSocketConfig implements WebSocketConfigurer {
//
//    @Autowired
//    private MyHandler myHandler;
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(myHandler, "/websocket/socketServer.do")
//                .addInterceptors(new SpringWebSocketHandlerInterceptor());
//        registry.addHandler(myHandler, "/sockjs/socketServer.do")
//                .addInterceptors(new SpringWebSocketHandlerInterceptor()).withSockJS();
//
//    }
//}
