package com.chen.webflux.websocket

import com.chen.webflux.websocket.annotation.WebSocketMapping

/**
 *@author 18066
 *@date 2020/7/12
 *@version 1.0
 */
@WebSocketMapping(["/message"])
class MessageWebSocketHandler : DefaultWebSocketHandler() {
}