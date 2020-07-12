package com.chen.webflux.websocket

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter

/**
 *@author 18066
 *@date 2020/7/9
 *@version 1.0
 */
class WebSocketAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun handlerAdapter(): WebSocketHandlerAdapter {
        return WebSocketHandlerAdapter()
    }


    @Bean
    fun messageWebSocketHandler(): MessageWebSocketHandler{
        return MessageWebSocketHandler()
    }

}