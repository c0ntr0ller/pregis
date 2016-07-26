var ws;

$(document).ready(function () {
    getWebConnect();
    $('.close-dialog').click(function () {
        hideErrorList();
    });
});

function getWebConnect() {
    var host = "ws://" + window.location.hostname + ":" + window.location.port + "/websocket";
    ws = new WebSocket(host);
    ws.onopen = function (event) {
        // console.log('Открыто соединение');
    };
    ws.onmessage = function (event) {
        var inMessage = event.data;
        // var scrollSize = 0;
        if (inMessage.indexOf('::setButtonState(false)') != -1) {
            showButton();
        } else if (inMessage.indexOf('::setButtonState(true)') != -1) {
            showState();
        } else if (inMessage.indexOf('::setFailed()') != -1) {
            setFailedLabelText();
        } else if (inMessage.indexOf('::setOkLabelText()') != -1) {
            setOkLabelText();
        } else if (inMessage.indexOf('::clearLabelText') != -1) {
            clearLabelText();
        } else if (inMessage.indexOf('::showModalWindow()') != -1) {
            showModalWindow(event.data.replace('::showModalWindow()', ''));
        } else {
            $('.label-text > span').html(inMessage);
            var $textarea = document.getElementById("messages");
            $textarea.value = $textarea.value + inMessage + "\n";

            // if (scrollSize === 0 || scrollSize === $textarea.scrollTop) {
                $textarea.scrollTop = $textarea.scrollHeight;
                // scrollSize = $textarea.scrollTop;
            // }
            // console.log('scrollTop: ' + $textarea.scrollTop + ' scrollHeight: ' + $textarea.scrollHeight + ' scrollSize:' + scrollSize);

        }
    };
    ws.onclose = function (event) {
        if (parseInt(event.code) === 1006 || parseInt(event.code) === 1008) {
            console.log('Обрыв соединения');
            // location.reload(true);  //если сохранить страницу на комп и запустить в цикле будет долбить сервер
            document.location.href = '/login';
            console.log('Код: ' + event.code + ' причина: ' + event.reason); // убрать
        } else if (event.wasClean) {
            // console.log('Соединение закрыто чисто');
        } else {
            console.log('Обрыв соединения'); // например, "убит" процесс сервера
            console.log('Код: ' + event.code + ' причина: ' + event.reason); // убрать
        }
        console.log('Код: ' + event.code + ' причина: ' + event.reason); // убрать
        // location.reload(true);  //если сохранить страницу на комп и запустить в цикле будет долбить сервер
        // getWebConnect();
    };
    ws.onerror = function (evt) {
        console.log("The following error occurred: " + evt.data);
    }
};

function sendMessage(message) {
    var valueForm = "";
    // sendMessage(message, "");
    if (ws.readyState != 1) location.reload(true);
    //     if (document.getElementById(message) !== null) {
    //         valueForm = document.getElementById(message).value;
    //     }
    var msgJSON = {
        command: message,
        value: valueForm
    };
    // ws.send(message);
    ws.send(JSON.stringify(msgJSON));
};

function setFailedLabelText() {
    $('.label-text').attr("class", "label-text failed");
//    $('.show-state .img').css("background-image", "url('images/fail66px.png')");
    $('.show-state .img').css("background-image", "url('images/error-64px.png')");
};
function setOkLabelText() {
    $('.show-state .img').css("background-image", "url('images/ok-64px.png')");
};
function clearLabelText() {
    $('.label-text').attr("class", "label-text");
    $('.show-state .img').css("background-image", "url('images/500.gif')");
};
function showModalWindow(text) {
    $('.modal-text').text(text);
    $('.modal-window').fadeIn(300);
    showLayoutHide()
};
function hideModalWindow() {
    $('.modal-window').fadeOut(300);
    hideLayoutHide();
};

