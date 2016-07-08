
var ws;

$(document).ready(function() {
getWebConnect();
getAjaxMessage();
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
        } else if (inMessage.indexOf('::setButtonState(true)') != -1) {
            setButtonState(true);
        } else if (inMessage.indexOf('::setFailed()') != -1 || inMessage.indexOf('::setOkLabelText()') != -1) {
        } else {
            var $textarea = document.getElementById("messages");
            $textarea.value = $textarea.value + inMessage + "\n";
            $textarea.scrollTop = $textarea.scrollHeight;
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
        getWebConnect();
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
function getAjaxMessage() {
    $('.forAjax > button').click(function () {

        var divCommand = $(this).parent().attr('id');
        var elementDiv = "#" + divCommand;

        var textId;
        var textValue;
        if ($(elementDiv).find('.textId') !== null) {
            textId = $(elementDiv).find('.textId').val();
        }
        if ($(elementDiv).find('.textValue') !== null) {
            textValue = $(elementDiv).find('.textValue').val();
        }
        console.log(divCommand + " id: " + textId + " value: " + textValue);
        var msgJSON = {
            command: divCommand,
            id: textId,
            value: textValue
        };
        $.post('/ajax', JSON.stringify(msgJSON), function (dataResponse) {
            var textarea = document.getElementById("messages");
            textarea.value = textarea.value + dataResponse + "\n";
        }, 'json').success(function () {
            console.log("Ajax done!")
        }).error(function () {
            console.log("Ajax fail!")
        }).complete(function () {
            console.log("Ajax complete!")
        });
        return false;
    });
};
