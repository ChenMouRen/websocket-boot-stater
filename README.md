# websocket-boot-stater

基于WebFlux的WebSocket解决方案,简化了WebSocket映射,使用Kotlin开发

## **注意事项**
本项目中已经包含了Web层框架WebFlux,项目中不要排除WebFlux或者导入SpringMvc否则会导致失效

## **使用说明**  
+ 克隆本项目到本地并进行安装
+ 项目中导入依赖  
```xml
    <groupId>com.chen.webflux</groupId>
    <artifactId>websocket-boot-stater</artifactId>
    <version>1.0.0-REALEASE</version>
```
+ 使用 com.chen.webflux.websocket.annotation.EnableWebSocket注解开启映射,默认包含一个路径为/message的映射,端口为http端口  
```java
@EnableWebSocket
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
```
+ 使用客户端连接
```javascript 1.8
  var webSocket = new WebSocket('ws://localhost:8080/message')
```
+ 向客户端发送消息
```java
    // 注入路径对应的Handler,/message为MessageWebSocketHandler
    @Autowired
    MessageWebSocketHandler messageWebSocketHandler;

    // 调用Handler的sendMessage方法
    messageWebSocketHandler.sendMessage(UUID.randonUUID.toString);
```
+ 接受客户端上传的消息,处理传输过程中发生的异常
```java
    // 注入路径对应的Handler,/message为MessageWebSocketHandler
    @Autowired
    MessageWebSocketHandler messageWebSocketHandler;

    // 设置Handler对应的messageConsumer,即可接收消息
    messageWebSocketHandler.setMessageConsumer(message -> {
        // message即为收到的消息字符串
    });
    
    // 设置Handler对应的errorConsumer,即可处理异常信息
    messageWebSocketHandler.setErrorConsumer(throwable -> {
        // throwable为异常信息
    });
```
+ 自定义WebSocket路径映射  
1.新建类继承com.chen.webflux.websocket.DefaultWebSocketHandler
```java
    import com.chen.webflux.websocket.annotation.EnableWebSocket;
    import org.springframework.stereotype.Component;
    import com.chen.webflux.websocket.DefaultWebSocketHandler;

    @Component
    @EnableWebSocket
    public class MyWebSocketHandler extends DefaultWebSocketHandler{
    }
```
2.新建或类实现org.springframework.web.reactive.socket.WebSocketHandler接口,并实现其方法
```java
    import com.chen.webflux.websocket.annotation.EnableWebSocket;
    import org.springframework.stereotype.Component;
    import org.springframework.web.reactive.socket.WebSocketHandler; 

    @Component
    @EnableWebSocket
    public class MyWebSocketHandler implements WebSocketHandler{

        @Override
        public Mono<Void> handle(WebSocketSession session) {
            // 自定义逻辑
            return null;
        }

    }
```
3.在类上加上com.chen.webflux.websocket.annotation.EnableWebSocket注解,同时让Spring扫描到该类  
