package za.co.fnb.mancala.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import za.co.fnb.mancala.config.GameConstants;
import za.co.fnb.mancala.controllers.data.CurrentPlayers;
import za.co.fnb.mancala.dto.RegistrationMessage;
import za.co.fnb.mancala.service.MessagingService;

@Controller
public class MessagingController {
	
	@Autowired
	private MessagingService notificationService;

	@Autowired
	private CurrentPlayers currentPlayers;
	
	@Autowired
	private GamesController gamesController;
		
	@MessageMapping("/register")
	public void processMessageFromClient(@Payload RegistrationMessage registrationMessage, SimpMessageHeaderAccessor headerAccessor)
			throws Exception {
		currentPlayers.addSession(registrationMessage.getSessionId(), registrationMessage.getName());
		
		//send register notification
		notificationService.notify(registrationMessage.getSessionId(), "You're registered, looking for an opponent.");			
	}

	@MessageMapping("/unregister")
	public void unregister(@Payload RegistrationMessage message, SimpMessageHeaderAccessor headerAccessor) throws Exception {
		currentPlayers.removeSession(message.getSessionId()); //TODO
	}
	
	@MessageMapping("/playmove")
	public void playmove(@Payload String message, SimpMessageHeaderAccessor headerAccessor) throws Exception {
		String[] split = message.split(GameConstants.PIT_SESSION_DEMARK);
		gamesController.playMove(split[0],split[1]); //TODO sort out this split thing
	}
}
