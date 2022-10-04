package za.co.fnb.mancala.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationService {
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	public void notify(String message, String sessionId) {
		messagingTemplate.convertAndSendToUser(sessionId, "/queue/notify", message, createHeaders(sessionId));
		
		//TODO split once ready
		messagingTemplate.convertAndSendToUser(sessionId, "/queue/gameupdate", message + " tooooooooooooooooooo", createHeaders(sessionId));
		return;
	}

	private MessageHeaders createHeaders(String sessionId) {
		SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
		headerAccessor.setSessionId(sessionId);
		headerAccessor.setLeaveMutable(true);
		return headerAccessor.getMessageHeaders();
	}
}