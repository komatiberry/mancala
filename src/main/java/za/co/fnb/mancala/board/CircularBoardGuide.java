package za.co.fnb.mancala.board;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import za.co.fnb.mancala.config.GameConstants;

@Component
//@Scope("singleton") //components are singleton by default
@Slf4j
public final class CircularBoardGuide {
	private List<String> circularList = new ArrayList<>();
	
	public CircularBoardGuide() {
		initialise();
	}
	
	private void initialise() {
		addItem(GameConstants.PIT_A1);
		addItem(GameConstants.PIT_A2);
		addItem(GameConstants.PIT_A3);
		addItem(GameConstants.PIT_A4);
		addItem(GameConstants.PIT_A5);
		addItem(GameConstants.PIT_A6);
		addItem(GameConstants.PLAYER_A_HOME_PIT);
		
		addItem(GameConstants.PIT_B1);
		addItem(GameConstants.PIT_B2);
		addItem(GameConstants.PIT_B3);
		addItem(GameConstants.PIT_B4);
		addItem(GameConstants.PIT_B5);
		addItem(GameConstants.PIT_B6);
		addItem(GameConstants.PLAYER_B_HOME_PIT);
	}

	private void addItem(String item) {
		circularList.add(item);
	}
	
	public String getNextItem(String currentItem) {
		if (circularList.isEmpty()) {
			log.error("Search done on empty board guide.");
			return null; 
		}
		
		int index = circularList.indexOf(currentItem);
		if (index == -1) {
			log.error("Invalid board guide item search : {}.", currentItem);
			return null;
		}
		
		if (circularList.size() > (index + 1)) {
			return circularList.get(index + 1); //get next
		} else {
			return circularList.get(0); //get the first one
		}
	}	
}