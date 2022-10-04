package za.co.fnb.mancala.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;
import za.co.fnb.mancala.controllers.data.CurrentPlayers;

@Configuration
@EnableScheduling
@Slf4j
public class SpringConfig {
	@Autowired
	CurrentPlayers currentPlayers;
	
	@PostConstruct
    @Scheduled(fixedRate = 10000)
	public void matchPlayers() {
		log.info("checking for orphaned players");
		currentPlayers.matchOrphanPlayers();
	}
	
	//TODO 
	// create scheduled task to check for stale players
		//if still connected ask them to make a move etc....
}
