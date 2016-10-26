var ws;
var scrollSize = 0;

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

        var inMessage = JSON.parse(event.data);

        // console.log("event.data: " + event.data);

        switch (inMessage.command) {
            case '::setButtonState(false)':
                showButton();
                break;
            case '::setButtonState(true)':
                showState();
                break;
            case '::setFailed()':
                setFailedLabelText();
                break;
            case '::setOkLabelText()':
                setOkLabelText();
                break;
            case '::clearLabelText()':
                clearLabelText();
                break;
            case '::showModalWindow()':
                showModalWindow(inMessage.value);
                break;
            case '::closeModalWindow()':
                hideModalWindow();
                break;
            default:
                showMessage(inMessage.value);
        }

        // if (inMessage.indexOf('::setButtonState(false)') != -1) {
        //     showButton();
        // } else if (inMessage.indexOf('::setButtonState(true)') != -1) {
        //     showState();
        // } else if (inMessage.indexOf('::setFailed()') != -1) {
        //     setFailedLabelText();
        // } else if (inMessage.indexOf('::setOkLabelText()') != -1) {
        //     setOkLabelText();
        // } else if (inMessage.indexOf('::clearLabelText') != -1) {
        //     clearLabelText();
        // } else if (inMessage.indexOf('::showModalWindow()') != -1) {
        //     showModalWindow(event.data.replace('::showModalWindow()', ''));
        // } else if (inMessage.indexOf('::closeModalWindow()') != -1) {
        //     hideModalWindow();
        // } else {
        //     showMessage();
        // }
    };
    ws.onclose = function (event) {
        if (parseInt(event.code) === 1006 || parseInt(event.code) === 1008) {
            // console.log('Обрыв соединения');
            // // location.reload(true);  //если сохранить страницу на комп и запустить в цикле будет долбить сервер
            document.location.href = '/login';
            // console.log('Код: ' + event.code + ' причина: ' + event.reason); // убрать
        // } else if (event.wasClean) {
            // console.log('Соединение закрыто чисто');
        // } else {
            // console.log('Обрыв соединения'); // например, "убит" процесс сервера
            // console.log('Код: ' + event.code + ' причина: ' + event.reason); // убрать
        }
        // console.log('Код: ' + event.code + ' причина: ' + event.reason); // убрать
        // location.reload(true);  //если сохранить страницу на комп и запустить в цикле будет долбить сервер
        // getWebConnect();
    };
    ws.onerror = function (evt) {
        console.log("The following error occurred: " + evt.data);
    };
};

function sendMessage(message) {
    sendMessageWithParam(message, "");
};
function sendMessageWithParam(message, param) {
    var valueForm = "";
    if (ws.readyState != 1) location.reload(true);
    if (param.length > 0) {
        valueForm = param;
    }
    var msgJSON = {
        command: message,
        value: valueForm
    };
    ws.send(JSON.stringify(msgJSON));
};
function showMessage(message) {
    $('.label-text > span').html(message);
    var $textarea = document.getElementById("messages");
    $textarea.value = $textarea.value + message + "\n";

    if (scrollSize === 0 || $textarea.scrollHeight < ($textarea.scrollTop + 500 )) {
        $textarea.scrollTop = $textarea.scrollHeight;
        scrollSize = $textarea.scrollTop;
    }
};
function setFailedLabelText() {
    $('.label-text').attr("class", "label-text failed");
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
    $('#message-modal-window').text(text);
    $('#view-modal-window').fadeIn(300);
    if ($('.hide-layout').css('display') != 'none') {
        $('.hide-layout').css("z-index", 1001);
    }
    showLayoutHide();
};
function showHouseListModalWindow(arrayHouse) {

    var selectBox = $('#house-select');

    selectBox.append($("<option></option>").attr("value", -1).prop("selected").text("Выгрузить все"));

    for (var i = 0; i < arrayHouse.length; i++) {
        var item = JSON.parse(arrayHouse[i]);
        selectBox.append($("<option></option>").attr("value", item.value).text(item.name));
    }
}
function hideModalWindow() {
    $('#view-modal-window').fadeOut(300);
    if ($('.hide-layout').css('z-index') === '1001') {
        $('.hide-layout').css("z-index", 998);
    }
    hideLayoutHide();
};
function answerNo() {
    hideModalWindow();
    sendMessageWithParam("callQuestion", "false");
};
function answerYes() {
    hideModalWindow();
    sendMessageWithParam("callQuestion", "true");
};

