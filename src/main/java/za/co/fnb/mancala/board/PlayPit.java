package za.co.fnb.mancala.board;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayPit {
	private String id;
	private boolean playable;
	private int numberOfStones;
}