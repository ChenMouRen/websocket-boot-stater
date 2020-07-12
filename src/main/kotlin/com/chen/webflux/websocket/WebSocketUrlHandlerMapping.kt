package com.chen.webflux.websocket

import com.chen.webflux.websocket.annotation.WebSocketMapping
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.util.StringUtils
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler

/**
 *@author 18066
 *@date 2020/7/9
 *@version 1.0
 */
class WebSocketUrlHandlerMapping : SimpleUrlHandlerMapping() {

    override fun initApplicationContext() {
        val webSocketUrlMap = mutableMapOf<String,WebSocketHandler>()
        applicationContext?.getBeansWithAnnotation(WebSocketMapping::class.java)
                ?.forEach { _, bean ->
                    run {
                        val beanClass = bean.javaClass
                        if (bean is WebSocketHandler) {
                            val webSocketMapping = AnnotationUtils.findAnnotation(beanClass, WebSocketMapping::class.java)
                            webSocketMapping?.url?.forEach { websocketUrl ->
                                run {
                                    if (StringUtils.hasText(websocketUrl) && websocketUrl.startsWith("/")) {
                                        if (webSocketUrlMap.containsKey(websocketUrl)) {
                                            log.warn("路径{}已存在Websocket映射,将不会重复映射", websocketUrl)
                                        } else {
                                            webSocketUrlMap[websocketUrl] = bean
                                            log.warn("路径{}映射为Websocket成功", websocketUrl)
                                        }

                                    }
                                }
                            }
                        } else {
                            log.warn("类{}不是WebSocketHandler子类,却添加了WebSocketMapping注解", beanClass.name)
                        }
                    }
                }
        urlMap = webSocketUrlMap
        super.setOrder(Ordered.HIGHEST_PRECEDENCE)
        super.initApplicationContext()
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(WebSocketUrlHandlerMapping::class.java)

    }
}