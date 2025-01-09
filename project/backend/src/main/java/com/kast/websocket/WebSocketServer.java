package com.kast.websocket;


import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.websocket.CloseReason;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.OnClose;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import io.micronaut.websocket.annotation.ServerWebSocket;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerWebSocket("/ws/matches/{id}")
public class WebSocketServer {
    private static final Logger LOG = LoggerFactory.getLogger(WebSocketServer.class);

    @OnOpen
    public Publisher<String> onOpen(String id, @NotNull WebSocketSession webSocketSession) {
        LOG.info("WebSocket new connection {} with match id = {}!", webSocketSession.getId(), id);
        return webSocketSession.send("Connected!");
    }

    @OnClose
    public void onClose(String id, @NotNull WebSocketSession webSocketSession) {
        LOG.info("Session closed {} with match id = {}", webSocketSession.getId(), id);
    }

    @OnMessage
    public Publisher<String> onMessage(String id, String message, @NotNull WebSocketSession webSocketSession) {
        LOG.info("Message received {} from session id {} with match id = {}", message, webSocketSession.getId(), id);

        if (message.equalsIgnoreCase("disconnect")) {
            LOG.info("Client close request!");
            webSocketSession.close(CloseReason.NORMAL);
            return Publishers.empty();
        }

        return webSocketSession.send("Not Supporter => (".concat(message).concat(")"));
    }
}