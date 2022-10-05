package za.co.fnb.mancala.controllers;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import za.co.fnb.mancala.board.Pit;

@Data
public class GameState {
	private List<Pit> pits = new ArrayList<>();
	private String currentPitMove;
}
