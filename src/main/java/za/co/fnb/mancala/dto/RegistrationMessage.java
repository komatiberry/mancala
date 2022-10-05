package za.co.fnb.mancala.dto;

import lombok.Data;

/**
 * Initial registration message sent from frontend.
 *
 */
@Data
public class RegistrationMessage {
	private String sessionId;
	private String name;
}