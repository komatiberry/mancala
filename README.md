# Game Macala

Uses Spring Framework, Websockets for duplex communications (specifically wanted to do server side pushes).
All default Spring configurations used unless specified in the Spring Config classes.
Please note that this project uses Lombok -  see https://projectlombok.org/ for details on set up.
On the frontend it uses Jquery and Javascript - badly hacked from various sources.

## Setup
Either launch from IDE with class MancalaApplication as the "main" entry point or use maven command "mvn spring-boot:run".
This will start the server on the default "localhost:8080".
In the future this can be easily changed to allow connections from outside the host server. 

##Play
This server allows multiple players to play from their own browser tabs.
There is no one browser tab "shared" mode - all players must individually connect to the host.

Once connected the player will be prompted for a name and be allowed to connect to a game.
The server will then try match any connected players so that they can play against each other.
Therefore to test, please open 2 browser tabs each pointing to localhost:8080 and "Connect to Game".
The server will then try match up the 2 players and automatically start a game between them.
You can have more than 2 sessions - I have not tested more than 4 concurrent sessions.
See "example-setup.jpg".

Then server will then provide playable buttons to click on for the player whose turn it is.

If a player drops off (e.g. closes their tab/browser) - then the server will try match the orphaned player with another unmatched session.
The server will check every 10 seconds for unmatched sessions and try match them - see the class "SpringConfig" if you need to change the time rate.

##The Good
The game should be totally playable from start to end for multiple players.
Happy with using server side push to make the game playable.
And while some of the data structures look a bit hacky they are fairly simple to work with.

##The Bad
The initial unit tests became redundant very quickly and I dropped all unit tests in favour of completing a playable project.
This is not good practise and I wish had more time.
Some of the code is not "clean code" and needs some refactoring e.g. GamesController.
Also I didn't use any functional style code in favour of ease of debugging, if I had more time I refactor to more immutability. 
Error handling could be improved but there is enough there for me to be happy with for now.
The design descisions regards using server side push paid off and helped reduce possible errors.

##The Ugly
The frontend look and the frontend code is butt ugly - but that wasn't part of the spec.
Otherwise I found jquery and the other Javascript libraries very powerful and easy to use.

