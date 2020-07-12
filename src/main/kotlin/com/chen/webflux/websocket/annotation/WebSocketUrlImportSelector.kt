package com.chen.webflux.websocket.annotation

import com.chen.webflux.websocket.MessageWebSocketHandler
import com.chen.webflux.websocket.WebSocketUrlHandlerMapping
import org.springframework.context.annotation.ImportSelector
import org.springframework.core.type.AnnotationMetadata

/**
 *@author 18066
 *@date 2020/7/9
 *@version 1.0
 */
class WebSocketUrlImportSelector : ImportSelector {

    override fun selectImports(importingClassMetadata: AnnotationMetadata): Array<String> {
        return arrayOf(WebSocketUrlHandlerMapping::class.java.name)
    }
}