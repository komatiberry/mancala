package za.co.fnb.mancala.controllers.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Player {
	private String sessionId;
	private String playerName;
	private boolean orphan = true;	
}
