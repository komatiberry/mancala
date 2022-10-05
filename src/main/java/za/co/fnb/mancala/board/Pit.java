package za.co.fnb.mancala.board;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Pit in the game board - both home and play pits.
 *
 */
@Data
@AllArgsConstructor
public class Pit {
	private String id;
	private boolean playable;
	private int numberOfStones;
}