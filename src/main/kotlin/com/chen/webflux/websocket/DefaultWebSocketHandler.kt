package com.chen.webflux.websocket

import org.slf4j.LoggerFactory
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import reactor.core.publisher.Mono
import java.util.concurrent.ConcurrentHashMap
import java.util.function.BiConsumer
import java.util.function.Consumer

/**
 *@author 18066
 *@date 2020/7/9
 *@version 1.0
 * 默认的websocket映射处理类,新建的可以直接继承此类
 */
open class DefaultWebSocketHandler : WebSocketHandler {

    companion object {
        private val log = LoggerFactory.getLogger(DefaultWebSocketHandler::class.java)
    }

    private val sessionFluxMap: ConcurrentHashMap<String, FluxSink<String?>> = ConcurrentHashMap()

    var messageConsumer: Consumer<String> = Consumer { message -> log.info("收到消息,消息内容为{}", message) }

    var errorConsumer: Consumer<Throwable> = Consumer { throwAble ->
        log.error("连接发生异常,异常信息为{}", throwAble)
    }

    override fun handle(session: WebSocketSession): Mono<Void> {
        val sessionId = session.id
        val input = session.receive()
                .doOnSubscribe { log.info("开启连接,sessionId为{}", sessionId) }
                .doOnNext { message: WebSocketMessage ->
                    messageConsumer.accept(message.payloadAsText)
                }
                .doOnCancel {
                    log.info("连接关闭,sessionId为{}", sessionId)
                    sessionFluxMap.remove(sessionId)
                }
                .doOnError {
                    errorConsumer.accept(it)
                    sessionFluxMap.remove(sessionId)
                }
                .then()
        val source = Flux.create { sink: FluxSink<String?> -> sessionFluxMap[sessionId] = sink }
        val outPut = session.send(source.map { payload: String? -> session.textMessage(payload!!) })
        return Mono.zip(input, outPut).then()
    }

    fun sendMessage(message: String) {
        sessionFluxMap.values.forEach { sink -> sink.next(message) }
    }
}