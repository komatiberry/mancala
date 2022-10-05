package za.co.fnb.mancala.board;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import za.co.fnb.mancala.config.GameConstants;
import za.co.fnb.mancala.controllers.data.Player;

@Data
public class GameBoard {
//	private boolean gameOver;
//	private String winner;
	private Player playerA;
	private Player playerB;
//	private List<PlayPit> playerAPits;
//	private List<PlayPit> playerAOpponentPits;
//	
//	private List<PlayPit> playerBPits;
//	private List<PlayPit> playerBOpponentPits;
//	private HomePit playerAHomePit;
//	private HomePit playerBHomePit;	
	
	
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
//		
//		
//		
//		playerAPits = Arrays.asList(
//				new PlayPit(GameConstants.PIT_A1, true, GameConstants.DEFAULT_NUMBER_OF_STONES),
//				new PlayPit(GameConstants.PIT_A2, true, GameConstants.DEFAULT_NUMBER_OF_STONES),
//				new PlayPit(GameConstants.PIT_A3, true, GameConstants.DEFAULT_NUMBER_OF_STONES),
//				new PlayPit(GameConstants.PIT_A3, true, GameConstants.DEFAULT_NUMBER_OF_STONES),
//				new PlayPit(GameConstants.PIT_A5, true, GameConstants.DEFAULT_NUMBER_OF_STONES),
//				new PlayPit(GameConstants.PIT_A6, true, GameConstants.DEFAULT_NUMBER_OF_STONES)
//				);
//
//		playerAOpponentPits = Arrays.asList(
//				new PlayPit(GameConstants.PIT_B1, false, GameConstants.DEFAULT_NUMBER_OF_STONES),
//				new PlayPit(GameConstants.PIT_B2, false, GameConstants.DEFAULT_NUMBER_OF_STONES),
//				new PlayPit(GameConstants.PIT_B3, false, GameConstants.DEFAULT_NUMBER_OF_STONES),
//				new PlayPit(GameConstants.PIT_B4, false, GameConstants.DEFAULT_NUMBER_OF_STONES),
//				new PlayPit(GameConstants.PIT_B5, false, GameConstants.DEFAULT_NUMBER_OF_STONES),
//				new PlayPit(GameConstants.PIT_B6, false, GameConstants.DEFAULT_NUMBER_OF_STONES)
//				);
//		
//		playerBPits = Arrays.asList(
//				new PlayPit(GameConstants.PIT_B1, false, GameConstants.DEFAULT_NUMBER_OF_STONES),
//				new PlayPit(GameConstants.PIT_B2, false, GameConstants.DEFAULT_NUMBER_OF_STONES),
//				new PlayPit(GameConstants.PIT_B3, false, GameConstants.DEFAULT_NUMBER_OF_STONES),
//				new PlayPit(GameConstants.PIT_B4, false, GameConstants.DEFAULT_NUMBER_OF_STONES),
//				new PlayPit(GameConstants.PIT_B5, false, GameConstants.DEFAULT_NUMBER_OF_STONES),
//				new PlayPit(GameConstants.PIT_B6, false, GameConstants.DEFAULT_NUMBER_OF_STONES)
//				);
//		
//		playerBOpponentPits = Arrays.asList(
//				new PlayPit(GameConstants.PIT_A1, false, GameConstants.DEFAULT_NUMBER_OF_STONES),
//				new PlayPit(GameConstants.PIT_A2, false, GameConstants.DEFAULT_NUMBER_OF_STONES),
//				new PlayPit(GameConstants.PIT_A3, false, GameConstants.DEFAULT_NUMBER_OF_STONES),
//				new PlayPit(GameConstants.PIT_A4, false, GameConstants.DEFAULT_NUMBER_OF_STONES),
//				new PlayPit(GameConstants.PIT_A5, false, GameConstants.DEFAULT_NUMBER_OF_STONES),
//				new PlayPit(GameConstants.PIT_A6, false, GameConstants.DEFAULT_NUMBER_OF_STONES)
//				);
//		
//		playerAHomePit = new HomePit(GameConstants.PLAYER_A_HOME_PIT, false, 0);
//		playerBHomePit = new HomePit(GameConstants.PLAYER_B_HOME_PIT, false, 0);
	}
}
