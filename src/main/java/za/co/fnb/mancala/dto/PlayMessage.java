package za.co.fnb.mancala.dto;

import lombok.Data;

@Data
public class PlayMessage {
	private String sessionId;
	private String selectedPit;
}