/**
 * Created by andryha on 24.05.2016.
 */
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
    ws.send(message);
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