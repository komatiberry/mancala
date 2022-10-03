package za.co.fnb.mancala.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HelloMessage {
	private String name;
	private String sessionId;
}
