package za.co.fnb.mancala.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import za.co.fnb.mancala.board.GameBoard;
import za.co.fnb.mancala.controllers.data.Player;

@Service
@Slf4j
public class GamesController {

	private Map<String, GameBoard> games = new HashMap<>();
	private Map<String, String> playerALookup = new HashMap<>();
	private Map<String, String> playerBLookup = new HashMap<>();
	
	public void addNewGame(Player playerA, Player playerB) {
		String gameKey = playerA.getSessionId() + playerB.getSessionId();
		playerALookup.put(playerA.getSessionId(), gameKey);
		playerBLookup.put(playerB.getSessionId(), gameKey);
		games.put(gameKey, new GameBoard(playerA, playerB));
		
		//TODO
		//send initial update to players
	}

	public void killGame(String sessionId) {
		String gameKey = findGameKey(sessionId);
		
		if (gameKey.isBlank()) { return; }		
		if (!games.containsKey(gameKey)) { return; }
		
		GameBoard game = games.get(gameKey);
		
		playerALookup.remove(game.getPlayerA().getSessionId());
		playerBLookup.remove(game.getPlayerB().getSessionId());
		
		games.remove(gameKey);
		log.info("Game {} removed", gameKey);
	}
	
	public String findGameKey(String sessionId) {
		String gameKey = playerALookup.get(sessionId);
		if (gameKey.isBlank()) { gameKey = playerBLookup.get(sessionId); }
		return gameKey;		
	}

	public String getOpponentKey(String sessionId) {
		String gameKey = findGameKey(sessionId);
		
		if (gameKey.isBlank()) { return null; }		
		if (!games.containsKey(gameKey)) { return null; }
		
		//TODO clean this ugh! code up
		GameBoard game = games.get(gameKey);
		if (game.getPlayerA().getSessionId().equalsIgnoreCase(sessionId)) {
			return game.getPlayerB().getSessionId(); //session is my opponent
		} else {
			return game.getPlayerA().getSessionId(); //else its me!
		}
	}
}
