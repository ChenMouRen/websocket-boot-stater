package com.chen.webflux.websocket.annotation

import org.springframework.context.annotation.Import

/**
 *@author 18066
 *@date 2020/7/9
 *@version 1.0
 * 开启websocket服务
 */
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
@Import(WebSocketUrlImportSelector::class)
annotation class EnableWebSocket {
}