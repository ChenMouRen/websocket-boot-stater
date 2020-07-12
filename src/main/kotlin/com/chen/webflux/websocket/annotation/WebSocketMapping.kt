package com.chen.webflux.websocket.annotation

import org.springframework.stereotype.Component

/**
 *@author 18066
 *@date 2020/7/9
 *@version 1.0
 * 将类标注为WebSocket映射路径
 */
@MustBeDocumented
@Target(AnnotationTarget.CLASS,AnnotationTarget.FUNCTION)
annotation class WebSocketMapping(val url: Array<String>) {

}