package za.co.fnb.mancala.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import lombok.extern.slf4j.Slf4j;
import za.co.fnb.mancala.controllers.data.CurrentPlayers;
import za.co.fnb.mancala.dto.Greeting;
import za.co.fnb.mancala.dto.HelloMessage;
import za.co.fnb.mancala.dto.PlayMessage;
import za.co.fnb.mancala.dto.RegistrationMessage;
import za.co.fnb.mancala.service.NotificationService;

@Controller
@Slf4j
public class EntryController {
	
	@Autowired
	private NotificationService notificationService;

	@Autowired
	public CurrentPlayers currentPlayers;
	
    @Autowired
    private SimpMessagingTemplate template;

	@MessageMapping("/hello")
//	@SendTo("/topic/greetings")
	public void greeting(HelloMessage message) throws Exception {
		for (int i = 0; i < 5; i++) {
			Thread.sleep(1000); // simulated delay
			greetingToo(new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName() + " " + i) + ", " + message.getSessionId() + " !"),  message.getSessionId());
		}
	}

	public void greetingToo(Greeting greeting, String sessionId) throws Exception {
		notificationService.notify(greeting.getContent(), sessionId);
		log.info("hello");
		this.template.convertAndSend("/topic/greetings", greeting);
	}
	
	
	@MessageMapping("/register")
	public void processMessageFromClient(@Payload RegistrationMessage registrationMessage, SimpMessageHeaderAccessor headerAccessor)
			throws Exception {
		currentPlayers.addSession(registrationMessage.getSessionId(), registrationMessage.getName());
		//send register notification
		
		//look for unmatched player
			//TODO synchronise this call
			//if successful send notification to both players
			//start game
			//send game update
		
			//if unsucessful
			//send notification
			
	}

	@MessageMapping("/unregister")
	public void unregister(@Payload RegistrationMessage message, SimpMessageHeaderAccessor headerAccessor) throws Exception {
		currentPlayers.removeSession(message.getSessionId()); //TODO
		
		//check if in game
			//if so send notification
			//end game
			//put other player back in pool
	}

	@MessageMapping("/playmove")
	public void playmove(@Payload PlayMessage message, SimpMessageHeaderAccessor headerAccessor) throws Exception {
//		currentPlayers.removeSession(message.getSessionId()); //TODO
	}

//	@Scheduled(fixedDelay = 5000)
//	public void receive()  {
//
//		String sessionId = globalProperties.getRandoSessionId();
//		if (sessionId != null) {
//			Notification notification = new Notification();
//			notification.setMessage("hello " + sessionId);
//			System.out.println("sending message : " + notification.getMessage());
//			notificationService.notify(notification.getMessage(), sessionId);
//		}
//	}
}
