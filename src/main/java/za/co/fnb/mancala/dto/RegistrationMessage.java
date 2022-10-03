package za.co.fnb.mancala.dto;

import lombok.Data;

@Data
public class RegistrationMessage {
	private String sessionId;
	private String name;
}