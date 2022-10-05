package za.co.fnb.mancala.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import za.co.fnb.mancala.dto.GameStateUpdate;

/**
 * WebSocket workhorse for sending updates to frontend.
 * Notify messages are "info" types.
 * GameUpdate for game state.
 *
 */
@Service
public class MessagingService {
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	public void notify(String sessionId, String message) {
		messagingTemplate.convertAndSendToUser(sessionId, "/queue/notify", message, createHeaders(sessionId));
	}
	
	public void gameUpdate(String sessionId, GameStateUpdate gameState) {
		messagingTemplate.convertAndSendToUser(sessionId, "/queue/gameupdate", gameState, createHeaders(sessionId));
		return;
	}

	private MessageHeaders createHeaders(String sessionId) {
		SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
		headerAccessor.setSessionId(sessionId);
		headerAccessor.setLeaveMutable(true);
		return headerAccessor.getMessageHeaders();
	}
}