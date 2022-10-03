package za.co.fnb.mancala.controllers;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GlobalProperties {
	private HashMap<String, String> sessionTransactionMap = new HashMap<String, String>();

	public void addSession(String sessionId, String playerName) {
		log.info("Adding session : {}, player name : {}", sessionId, playerName);
		this.sessionTransactionMap.put(sessionId, playerName);
	}

	public void removeSession(String sessionId) {
		log.info("Removing session : {}" + sessionId);
		this.sessionTransactionMap.remove(sessionId);
	}
}
