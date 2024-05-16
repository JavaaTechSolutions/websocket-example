package com.jts.websocket.config;

import java.util.Objects;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.jts.websocket.service.Message;
import com.jts.websocket.service.MsgType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {
	
	private final SimpMessageSendingOperations messagOperations;

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		String username = (String) headerAccessor.getSessionAttributes().get("username");
		
		if (Objects.nonNull(username)) {
			log.info("User disconnected: {}", username);
			
			messagOperations.convertAndSend("/topic/chat", Message.builder().type(MsgType.LEAVE).sender(username).build());
		}
	}
}
