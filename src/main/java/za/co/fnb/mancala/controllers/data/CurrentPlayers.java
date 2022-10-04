package za.co.fnb.mancala.controllers.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import za.co.fnb.mancala.controllers.GamesController;
import za.co.fnb.mancala.service.NotificationService;

@Component
@Slf4j
public class CurrentPlayers {
	private HashMap<String, Player> sessionMap = new HashMap<>();
	
	@Autowired
	NotificationService notificationService;
	
	@Autowired
	private GamesController gamesController;

	public void addSession(String sessionId, String playerName) {
		Player player = new Player(sessionId, playerName, true);
		log.info("Adding session : {}, player name : {}", sessionId, playerName);
		this.sessionMap.put(sessionId, player);
	}

	public void removeSession(String sessionId) {
		log.info("Removing session : {}" + sessionId);
		
		//orphan the other opponent
		String opponentKey = gamesController.getOpponentKey(sessionId);		
		sessionMap.get(opponentKey).setOrphan(true);
		notificationService.notify("Your opponent has dropped out, we'll find you another once they log on - or something like that. Hang in there champ!", opponentKey);
		//TODO reset their board
		
		gamesController.killGame(sessionId);
		this.sessionMap.remove(sessionId);
	}
	
	public void matchOrphanPlayers() {
		//TODO look at converting to functional style
		Player playerA = null;
		for (Map.Entry<String, Player> entry : sessionMap.entrySet()) {
			Player player = entry.getValue();
			if (player.isOrphan()) {
				if (Objects.nonNull(playerA)) {
					//match made
					log.info("Match found for {} vs {}", playerA.getPlayerName(), player.getPlayerName());
					gamesController.addNewGame(playerA, player);
					
					//TODO check that this doesn't break things
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