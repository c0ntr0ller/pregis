
var ws;

function init() {
    ws = new WebSocket("ws://localhost:8080/websocket");
    ws.onopen = function (event) {

    };
    ws.onmessage = function (event) {
        var inMessage = event.data;
        if (inMessage.includes('::setButtonState(false)')) {
            setButtonState(false);
        } else if (inMessage.includes('::setButtonState(true)')) {
            setButtonState(true);
        } else {
            var $textarea = document.getElementById("messages");
            $textarea.value = $textarea.value + inMessage + "\n";
            $textarea.scrollTop = $textarea.scrollHeight;
        }
    };
    ws.onclose = function (event) {
        // location.reload(true);
        document.location.href = '/login';
    };
    ws.onerror = function (evt) {
        console.log("The following error occurred: " + evt.data);
    }

};
function sendMessage(message) {    
    setButtonState(true);
    var valueForm = "";
    if (document.getElementById(message) !== null) {
        valueForm = document.getElementById(message).value;
    }
    var msgJSON = {
        command: message,
        value: valueForm
    };
    // ws.send(message);
    ws.send(JSON.stringify(msgJSON));
};
function setButtonState(state) {
    var allButton = document.getElementsByTagName('BUTTON').length;
    var buttons = document.getElementsByTagName('BUTTON');
    for (var i = 0; i < allButton; i++) buttons[i].disabled=state;
}
function getSessionID(tesxt) {
    var sessionID = document.cookie;
    sendMessage(sessionID + " : " + tesxt);
}