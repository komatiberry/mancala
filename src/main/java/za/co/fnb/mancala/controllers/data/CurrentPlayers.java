package za.co.fnb.mancala.controllers.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import za.co.fnb.mancala.controllers.GamesController;
import za.co.fnb.mancala.service.MessagingService;

/**
 * Handles session/player management.
 * Technically more than just a Data object.
 * Needs some love.
 */
@Component
@Slf4j
public class CurrentPlayers {
	private HashMap<String, Player> sessionMap = new HashMap<>();
	
	@Autowired
	private MessagingService messagingService;
	
	@Autowired
	private GamesController gamesController;

	public void addSession(String sessionId, String playerName) {
		Player player = new Player(sessionId, playerName, true);
		log.info("Adding session : {}, player name : {}", sessionId, playerName);
		this.sessionMap.put(sessionId, player);
	}

	public void removeSession(String sessionId) {
		log.info("Removing session : {}" + sessionId);
	
		Player player = sessionMap.get(sessionId);
		
		if (!player.isOrphan()) {
			//orphan the opponent
			String opponentKey = gamesController.getOpponentKey(sessionId);	
			if (opponentKey != null && !opponentKey.isBlank()) {
				sessionMap.get(opponentKey).setOrphan(true);
				messagingService.notify(opponentKey, "Your opponent has dropped out, we'll find you another once they log on - or something like that. Hang in there champ!");
				//TODO reset opponent board
				
				gamesController.killGame(sessionId);
			}
		}
		
		this.sessionMap.remove(sessionId);
	}
	
	public void matchOrphanPlayers() {
		//TODO look at converting to functional style
		Player playerA = null;
		for (Map.Entry<String, Player> entry : sessionMap.entrySet()) {
			Player player = entry.getValue();
			if (player.isOrphan()) {
				if (Objects.nonNull(playerA)) {

					log.info("Match found for {} vs {}", playerA.getPlayerName(), player.getPlayerName());
					gamesController.addNewGame(playerA, player);
					
					player.setOrphan(false);
					playerA.setOrphan(false);
					log.info("Matching complete for {} vs {}", playerA.getPlayerName(), player.getPlayerName());
					
					playerA = null;					

				} else {
					playerA = player;
				}
			}
		}
	}
}