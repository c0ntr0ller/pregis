
var ws;

$(document).ready(function() {
    var host = "ws://" + window.location.hostname +":" + window.location.port + "/websocket";
    ws = new WebSocket(host);
    // console.log(host);
    // ws = new WebSocket("ws://localhost:8080/websocket");
    ws.onopen = function (event) {

    };
    ws.onmessage = function (event) {
        var inMessage = event.data;
        if (inMessage.indexOf('::setButtonState(false)') != -1) {
            setButtonState(false);
            showButton();
        } else if (inMessage.indexOf('::setButtonState(true)') != -1) {
            setButtonState(true);
            showState();
        } else if (inMessage.indexOf('::setFailed()') != -1) {
            setFailed();
        } else {
            var $textarea = document.getElementById("messages");
            $('.label-text').html(inMessage);
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

});

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
function setFailed() {
    $('.label-text').attr("class", "label-text failed");
    var cssTemp = $('.show-state img').attr("src");
    // $('.show-state img').attr("src", "html/test/fail66px.png");
    $('.show-state img').attr("src", "html/test/ok64px.png");
}