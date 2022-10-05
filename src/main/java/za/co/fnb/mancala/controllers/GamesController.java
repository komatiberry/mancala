package za.co.fnb.mancala.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import za.co.fnb.mancala.board.CircularBoardGuide;
import za.co.fnb.mancala.board.GameBoard;
import za.co.fnb.mancala.board.Pit;
import za.co.fnb.mancala.config.GameConstants;
import za.co.fnb.mancala.controllers.data.Player;
import za.co.fnb.mancala.service.MessagingService;

@Service
@Slf4j
public class GamesController {

	private Map<String, GameBoard> games = new HashMap<>();
	private Map<String, String> playerALookup = new HashMap<>();
	private Map<String, String> playerBLookup = new HashMap<>();
	
	@Autowired
	MessagingService messagingService;
	
	@Autowired
	CircularBoardGuide guide; //TODO make it a static class?
	
	public void addNewGame(Player playerA, Player playerB) {
		String gameKey = playerA.getSessionId() + playerB.getSessionId();
		playerALookup.put(playerA.getSessionId(), gameKey);
		playerBLookup.put(playerB.getSessionId(), gameKey);
		GameBoard gameBoard = new GameBoard(playerA, playerB);
		games.put(gameKey, gameBoard);

		initialState(gameBoard);
	}

	private void initialState(GameBoard gameBoard) {
		//send initial state to players
		GameState gameStateA = new GameState();
		for (Pit pit : gameBoard.getPits().values()) {
			gameStateA.getPits().add(pit);
	    }

		messagingService.gameUpdate(gameBoard.getPlayerA().getSessionId(), gameStateA);
		messagingService.notify(gameBoard.getPlayerA().getSessionId(), "Opponent found! Opponent's name is " + gameBoard.getPlayerB().getPlayerName() + ", you're Player A - make your move.");
		
		GameState gameStateB = new GameState();
		for (Pit pit : gameBoard.getPits().values()) {
			gameStateB.getPits().add(pit);
	    }
		for (Pit pit : gameStateB.getPits()) {
			pit.setPlayable(false);
		}
	
		messagingService.gameUpdate(gameBoard.getPlayerB().getSessionId(), gameStateB);
		messagingService.notify(gameBoard.getPlayerB	().getSessionId(), "Opponent found! Opponent's name is " + gameBoard.getPlayerA().getPlayerName() + ", you're Player B - wait your turn.");
	}

	public void killGame(String sessionId) {
		String gameKey = findGameKey(sessionId);
		
		String leftOverPlayer = getOpponentKey(sessionId);
		
		if (gameKey.isBlank()) { return; }		
		if (!games.containsKey(gameKey)) { return; }
		
		GameBoard game = games.get(gameKey);
		
		playerALookup.remove(game.getPlayerA().getSessionId());
		playerBLookup.remove(game.getPlayerB().getSessionId());
		
		games.remove(gameKey);
		log.info("Game {} removed", gameKey);
		
		//zero out left over player's board
		GameBoard tempBoard = new GameBoard(null, null);
		GameState gameState = new GameState();
		for (Pit pit : tempBoard.getPits().values()) {
			gameState.getPits().add(pit);
	    }
		for (Pit pit : gameState.getPits()) {
			pit.setPlayable(false);
			pit.setNumberOfStones(0);
		}
		messagingService.gameUpdate(leftOverPlayer, gameState);
	}
	
	public String findGameKey(String sessionId) {
		String gameKey = playerALookup.get(sessionId);
		if (gameKey == null || gameKey.isBlank()) { gameKey = playerBLookup.get(sessionId); }
		return gameKey;		
	}

	public String getOpponentKey(String sessionId) {
		String gameKey = findGameKey(sessionId);
		
		if (gameKey == null || gameKey.isBlank()) { return null; }		
		if (!games.containsKey(gameKey)) { return null; }
		
		//TODO clean this ugh! code up
		GameBoard game = games.get(gameKey);
		if (game.getPlayerA().getSessionId().equalsIgnoreCase(sessionId)) {
			return game.getPlayerB().getSessionId(); //session is my opponent
		} else {
			return game.getPlayerA().getSessionId(); //else its me!
		}
	}

	//TODO clean up this code
	//TODO add tests
	//TODO show every turn
	public void playMove(String playerSessionId, String pitPlayed) {
		GameBoard board = games.get(findGameKey(playerSessionId));
		Pit currentPit = board.getPits().get(pitPlayed);

		Pit homePit = null;
		String nextPlayerToPlay = "";
		if (pitPlayed.startsWith("A")) {
			nextPlayerToPlay = "B";
			homePit = board.getPits().get(GameConstants.PLAYER_A_HOME_PIT);
		} else {
			nextPlayerToPlay = "A";
			homePit = board.getPits().get(GameConstants.PLAYER_B_HOME_PIT);
		}
		
		int stonesToPlay =  currentPit.getNumberOfStones();
		log.info("Played pit stones = {}", stonesToPlay);
		currentPit.setNumberOfStones(0);
		currentPit.setPlayable(false); //'cause its zero

		while (stonesToPlay > 0) {		
			currentPit = board.getPits().get(guide.getNextItem(currentPit.getId()));
			
			//check that we're not updating opponent's home pit
			if (currentPit.getId().equalsIgnoreCase(GameConstants.PLAYER_A_HOME_PIT)) {
				if (pitPlayed.startsWith("B")) {
					currentPit = board.getPits().get(guide.getNextItem(currentPit.getId()));
				}
			} else if (currentPit.getId().equalsIgnoreCase(GameConstants.PLAYER_B_HOME_PIT)) {
				if (pitPlayed.startsWith("A")) {
					currentPit = board.getPits().get(guide.getNextItem(currentPit.getId()));
				}
			}
			
			//check if new "homeside" target pit is empty, in which case steal opposite pit's stones into home pit
			if (currentPit.getNumberOfStones() == 0 && !currentPit.getId().contains(GameConstants.HOME_PIT_IDENTIFIER) 
					&& (pitPlayed.charAt(0) == currentPit.getId().charAt(0))) {
				
				Pit oppositePit = board.getPits().get(guide.getOppositePitKey(currentPit.getId()));
				homePit.setNumberOfStones(homePit.getNumberOfStones() + oppositePit.getNumberOfStones());
				oppositePit.setNumberOfStones(0);
			}
			
			//update current pit stones
			currentPit.setNumberOfStones(currentPit.getNumberOfStones() + 1);
			stonesToPlay--;
			
			//if we're home on last stone then we get to go again
			if (stonesToPlay == 0) {
				//check if we're on home pit
				if (currentPit.getId().equalsIgnoreCase(GameConstants.PLAYER_A_HOME_PIT)) {
					if (pitPlayed.startsWith("A")) {
						nextPlayerToPlay = "A";
					}
				} else if (currentPit.getId().equalsIgnoreCase(GameConstants.PLAYER_B_HOME_PIT)) {
					if (pitPlayed.startsWith("B")) {
						nextPlayerToPlay = "A";
					}
				}
			}
		} //end while
		
		
		//TODO fold both loops into one
		//update A's state
		boolean allZero = true;
		GameState gameStateA = new GameState();
		for (Pit pit : board.getPits().values()) {
			//check win condition
			if (pit.getId().startsWith("A") && pit.getNumberOfStones() > 0
					&& !pit.getId().equalsIgnoreCase(GameConstants.PLAYER_A_HOME_PIT)) { allZero = false; }
			
			//set playable flag on pits
			if (nextPlayerToPlay.equalsIgnoreCase("A")) {
				if (pit.getId().startsWith("A") 
						&& !pit.getId().equalsIgnoreCase(GameConstants.PLAYER_A_HOME_PIT)
						&& pit.getNumberOfStones() > 0) {
					pit.setPlayable(true);
				} else {
					pit.setPlayable(false);
				}
			} else { pit.setPlayable(false); }
			gameStateA.getPits().add(pit);
	    }
		
		if (allZero) { 
			winner(board.getPlayerA(), board.getPlayerB(), board);
			return;
		}
		
		messagingService.gameUpdate(board.getPlayerA().getSessionId(), gameStateA);
		String message = "Please wait your turn.";
		if (nextPlayerToPlay.equalsIgnoreCase("A")) { message = "Your turn please play"; }
		messagingService.notify(board.getPlayerA().getSessionId(), message);
		
		//update B's state
		allZero = true;
		GameState gameStateB = new GameState();
		for (Pit pit : board.getPits().values()) {
			//check win condition
			if (pit.getId().startsWith("B") && pit.getNumberOfStones() > 0
					&& !pit.getId().equalsIgnoreCase(GameConstants.PLAYER_B_HOME_PIT)) { allZero = false; }
			
			//set playable flag on pits
			if (nextPlayerToPlay.equalsIgnoreCase("B")) {
				if (pit.getId().startsWith("B") 
						&& !pit.getId().equalsIgnoreCase(GameConstants.PLAYER_B_HOME_PIT)
						&& pit.getNumberOfStones() > 0) {
					pit.setPlayable(true);
				} else {
					pit.setPlayable(false);
				}
			} else { pit.setPlayable(false); }
			gameStateB.getPits().add(pit);
	    }
		
		if (allZero) { 
			winner(board.getPlayerB(), board.getPlayerA(), board);
			return;
		}
		
		messagingService.gameUpdate(board.getPlayerB().getSessionId(), gameStateB);
		message = "Please wait your turn.";
		if (nextPlayerToPlay.equalsIgnoreCase("B")) { message = "Your turn please play"; }
		messagingService.notify(board.getPlayerB().getSessionId(), message);
	}

	private void winner(Player winner, Player loser, GameBoard board) {
		log.info("We have a winner!!!!!!!!!!!!!!! : {}", winner.getPlayerName());
		messagingService.notify(winner.getSessionId(),"You've won!!!!");
		messagingService.notify(loser.getSessionId(),"Sorry you've lost.");
		
		try {
			Thread.sleep(5000); //enjoy 5 seconds of joy
		} catch (InterruptedException e1) {}
		
		killGame(loser.getSessionId());
		
		addNewGame(winner, loser);
	}
}
