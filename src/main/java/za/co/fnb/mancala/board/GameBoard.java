package za.co.fnb.mancala.board;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import za.co.fnb.mancala.config.GameConstants;
import za.co.fnb.mancala.controllers.data.Player;

/**
 * GameBoard contains the game state for 2 players
 *
 */
@Data
public class GameBoard {
	//TODO rethink this "playerA", "playerB" convention - if time allows
	private Player playerA;
	private Player playerB;
	
	private Map<String, Pit> pits = new HashMap<>();
	
	public GameBoard(Player playerA, Player playerB) {
		this.playerA = playerA;
		this.playerB = playerB;
		initialiseBoard();
	}
	
	private void initialiseBoard() {
		pits.put(GameConstants.PIT_A1, new PlayPit(GameConstants.PIT_A1, true, GameConstants.DEFAULT_NUMBER_OF_STONES));
		pits.put(GameConstants.PIT_A2, new PlayPit(GameConstants.PIT_A2, true, GameConstants.DEFAULT_NUMBER_OF_STONES));
		pits.put(GameConstants.PIT_A3, new PlayPit(GameConstants.PIT_A3, true, GameConstants.DEFAULT_NUMBER_OF_STONES));
		pits.put(GameConstants.PIT_A4, new PlayPit(GameConstants.PIT_A4, true, GameConstants.DEFAULT_NUMBER_OF_STONES));
		pits.put(GameConstants.PIT_A5, new PlayPit(GameConstants.PIT_A5, true, GameConstants.DEFAULT_NUMBER_OF_STONES));
		pits.put(GameConstants.PIT_A6, new PlayPit(GameConstants.PIT_A6, true, GameConstants.DEFAULT_NUMBER_OF_STONES));
		pits.put(GameConstants.PLAYER_A_HOME_PIT, new HomePit(GameConstants.PLAYER_A_HOME_PIT, false, 0));
		
		pits.put(GameConstants.PIT_B1, new PlayPit(GameConstants.PIT_B1, false, GameConstants.DEFAULT_NUMBER_OF_STONES));
		pits.put(GameConstants.PIT_B2, new PlayPit(GameConstants.PIT_B2, false, GameConstants.DEFAULT_NUMBER_OF_STONES));
		pits.put(GameConstants.PIT_B3, new PlayPit(GameConstants.PIT_B3, false, GameConstants.DEFAULT_NUMBER_OF_STONES));
		pits.put(GameConstants.PIT_B4, new PlayPit(GameConstants.PIT_B4, false, GameConstants.DEFAULT_NUMBER_OF_STONES));
		pits.put(GameConstants.PIT_B5, new PlayPit(GameConstants.PIT_B5, false, GameConstants.DEFAULT_NUMBER_OF_STONES));
		pits.put(GameConstants.PIT_B6, new PlayPit(GameConstants.PIT_B6, false, GameConstants.DEFAULT_NUMBER_OF_STONES));
		pits.put(GameConstants.PLAYER_B_HOME_PIT, new HomePit(GameConstants.PLAYER_B_HOME_PIT, false, 0));
	}
}
