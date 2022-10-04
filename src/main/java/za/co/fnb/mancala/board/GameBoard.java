package za.co.fnb.mancala.board;

import java.util.Arrays;
import java.util.List;

import lombok.Data;
import za.co.fnb.mancala.config.GameConstants;
import za.co.fnb.mancala.controllers.data.Player;

@Data
public class GameBoard {
	private boolean gameOver;
	private String winner;
	private Player playerA;
	private Player playerB;
	private List<PlayPit> playerAPits;
	private List<PlayPit> playerBPits;
	private HomePit playerAHomePit;
	private HomePit playerBHomePit;
	
	
	public GameBoard(Player playerA, Player playerB) {
		this.playerA = playerA;
		this.playerB = playerB;
		initialiseBoard();
	}
	
	private void initialiseBoard() {
		playerAPits = Arrays.asList(
				new PlayPit(GameConstants.PIT_A1, true, GameConstants.DEFAULT_NUMBER_OF_STONES),
				new PlayPit(GameConstants.PIT_A2, true, GameConstants.DEFAULT_NUMBER_OF_STONES),
				new PlayPit(GameConstants.PIT_A3, true, GameConstants.DEFAULT_NUMBER_OF_STONES),
				new PlayPit(GameConstants.PIT_A4, true, GameConstants.DEFAULT_NUMBER_OF_STONES),
				new PlayPit(GameConstants.PIT_A5, true, GameConstants.DEFAULT_NUMBER_OF_STONES),
				new PlayPit(GameConstants.PIT_A6, true, GameConstants.DEFAULT_NUMBER_OF_STONES)
				);
		
		playerBPits = Arrays.asList(
				new PlayPit(GameConstants.PIT_B1, true, GameConstants.DEFAULT_NUMBER_OF_STONES),
				new PlayPit(GameConstants.PIT_B2, true, GameConstants.DEFAULT_NUMBER_OF_STONES),
				new PlayPit(GameConstants.PIT_B3, true, GameConstants.DEFAULT_NUMBER_OF_STONES),
				new PlayPit(GameConstants.PIT_B4, true, GameConstants.DEFAULT_NUMBER_OF_STONES),
				new PlayPit(GameConstants.PIT_B5, true, GameConstants.DEFAULT_NUMBER_OF_STONES),
				new PlayPit(GameConstants.PIT_B6, true, GameConstants.DEFAULT_NUMBER_OF_STONES)
				);
		
		playerAHomePit = new HomePit(GameConstants.PLAYER_A_HOME_PIT, 0);
		playerBHomePit = new HomePit(GameConstants.PLAYER_B_HOME_PIT, 0);
	}
}
