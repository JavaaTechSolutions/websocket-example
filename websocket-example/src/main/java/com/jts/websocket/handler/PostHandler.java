package com.jts.websocket.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostHandler implements WebSocketHandler {

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("Connection established on session: {}", session.getId());
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		String post = (String) message.getPayload();
		log.info("Post details {}", post);
		session.sendMessage(new TextMessage("Processing started for session " + session + " - " + post ));
		Thread.sleep(500);
		session.sendMessage(new TextMessage("Processing completed for session " + session + " - " + post ));
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		log.info("Exception occured: {} on session: {}", exception.getMessage(), session.getId());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		log.info("Connection closed on session: {} with status: {}", session.getId(), closeStatus.getCode());
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

}
