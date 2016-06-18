
var ws;

$(document).ready(function() {
getWebConnect();
});

function getWebConnect() {
        var host = "ws://" + window.location.hostname +":" + window.location.port + "/websocket";
    ws = new WebSocket(host);
    ws.onopen = function (event) {
        // console.log('Открыто соединение');
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
            setFailedLabelText();
        } else {
            var $textarea = document.getElementById("messages");
            $('.label-text').html(inMessage);
            $textarea.value = $textarea.value + inMessage + "\n";
            $textarea.scrollTop = $textarea.scrollHeight;
            clearLabelText();
        }
    };
    ws.onclose = function (event) {
        if (event.wasClean) {
           // console.log('Соединение закрыто чисто');
        } else if (event.code === 1006 || event.code === 1008) {
            console.log('Обрыв соединения');
            // location.reload(true);  //если сохранить страницу на комп и запустить в цикле будет долбить сервер
            document.location.href = '/login';
        } else {
           console.log('Обрыв соединения'); // например, "убит" процесс сервера
           console.log('Код: ' + event.code + ' причина: ' + event.reason); // убрать
        }
        // getWebConnect();
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
};
function getSessionID(tesxt) {
    var sessionID = document.cookie;
    sendMessage(sessionID + " : " + tesxt);
};
function setFailedLabelText() {
    $('.label-text').attr("class", "label-text failed");
    var cssTemp = $('.show-state .img').css("background");
    $('.show-state .img').css("background-image", "url('html/test/error-64px.png')");
};
function setOkLabelText() {
    $('.show-state .img').css("background-image", "url('html/test/ok-64px.png')");
};
function clearLabelText() {
    $('.label-text').attr("class", "label-text");
    $('.show-state .img').css("background-image", "url('html/test/500.png')");
};