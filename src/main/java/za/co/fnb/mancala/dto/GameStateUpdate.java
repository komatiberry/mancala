package za.co.fnb.mancala.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import za.co.fnb.mancala.board.Pit;

/**
 * This is the data sent to the frontend to update its game state.
 *
 */
@Data
public class GameStateUpdate {
	private List<Pit> pits = new ArrayList<>();
	private String currentPitMove; //TODO check if this is redundant.
}
