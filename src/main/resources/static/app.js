var stompClient = null;
var sessionId = null;

//function setConnected(connected) {
//    $("#connect").prop("disabled", connected);
//    $("#disconnect").prop("disabled", !connected);
//    if (connected) {
//        $("#conversation").show();
//    }
//    else {
//        $("#conversation").hide();
//    }
//    $("#greetings").html("");
//}

function connect() {
	var name = $("#name").val();
	if (name) {
	    var socket = new SockJS('/game-endpoint');
	    stompClient = Stomp.over(socket);
	
		// Subscribe the '/notify' channell
		stompClient.connect({}, function(frame) {
			//logic to extract sessionid
			var urlarray = socket._transport.url.split('/');
			var index = urlarray.length - 2;
			sessionId = urlarray[index];
			$('#sessionIdLabel').html(sessionId);
			
			//client will subscribe for updates on this endpoint
			stompClient.subscribe('/user/queue/notify', function(
					notification) {
				// Call the notify function when receive a notification
				showNotificationUpdate(notification.body);
		
			});
			
			//client will subscribe for updates on this endpoint
			stompClient.subscribe('/user/queue/gameupdate', function(
					notification) {
				// Call the notify function when receive a notification
				showGameUpdate(notification.body);
		
			});
			
			//App should register itself by calling this method
			register(sessionId, name);
			
					//TODO remove below once done
			var message = {
				"sessionId" : sessionId,
				"name": $("#name").val()
			};
			var stringMessage = JSON.stringify(message);
		    stompClient.send("/app/hello", {}, stringMessage);
		});

		
	    const sendButton = document.getElementById("send");
    	if (sendButton) { sendButton.remove(); }
    	
    	const nameField = document.getElementById("name");
    	if (nameField) { nameField.disabled = true; }
    	
    	const nameLabel = document.getElementById("name-label");
    	if (nameLabel) {nameLabel.textContent = "Welcome : " }
    } else { 
		alert("Please enter your name!" );
	} 

}

function register(sessionId, name) {
	var message = {
		"sessionId" : sessionId,
		"name" : name
	};
	var stringMessage = JSON.stringify(message);
	console.log(stringMessage);
	stompClient.send("/app/register", {}, stringMessage);
}

function sendName() {
	var name = $("#name").val();
	if (name) {
		var message = {
			"sessionId" : sessionId,
			"name": $("#name").val()
		};
		var stringMessage = JSON.stringify(message);
	    stompClient.send("/app/hello", {}, stringMessage);
	    
	    const element = document.getElementById("send");
    	if (element) { element.remove(); }
    } else { 
		alert("Please enter your name!" );
	} 

}

function showNotificationUpdate(message) {
	const element = document.getElementById("message");
    if (element) { element.remove(); }
    
    $("#messages").append("<tr id=\"message\"><td>" + message + "</td></tr>");
}

function showGameUpdate(gameState) {
	const gs = JSON.parse(gameState);
	for (let i in gs.pits) {
		updatePit(gs.pits[i]);
	} 

}

function updatePit(pitData) {
	const pit = document.getElementById(pitData.id);

	if (pit) {
		$("#" + pitData.id).empty(); 

		if (pitData.playable === true) {
            // TODO find a better way than "---"
			$("#" + pitData.id).wrapInner('<input id=' + sessionId + '---' + pitData.id +' onclick=playMove(this) type=button value=' + pitData.numberOfStones +' class=button button1/>');
		} else {
			$("#" + pitData.id).append(pitData.numberOfStones);
		}
	}
}

function playMove(x) {
	console.log(x.id);
	var stringMessage = JSON.stringify(x.id);
	stompClient.send("/app/playmove", {}, x.id);
}

//when the window gets closed
window.onbeforeunload = function(e) {
	// Call this method when logging off also
	unregister(sessionId);
};

function unregister(sessionId) {
	var message = {
		"sessionId" : sessionId
	};
	var stringMessage = JSON.stringify(message);
	console.log(stringMessage);
	stompClient.send("/app/unregister", {}, stringMessage);
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
//    $( "#connect" ).click(function() { connect(); });

    $( "#send" ).click(function() { connect(); });
});