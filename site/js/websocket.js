var ws;
var scrollSize = 0;
var tempCommandID = "";

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
            case '::showHouseListModalWindow()':
                showHouseListModalWindow(inMessage.value);
                break;
            default:
                showMessage(inMessage.value);
        }
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
    // console.log("Send : " + msgJSON);
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

    $('#message-modal-window').text("");
    $('#message-modal-window').append($("<select></select>").attr("id", 'house-select'));

    var selectBox = $('#house-select');

    var arrayJSON = JSON.parse(arrayHouse);

    if (arrayJSON.length > 0 ) {
        selectBox.append($("<option></option>").attr("value", -1).text("Выгрузить все"));
    }
    if (arrayJSON.length > 0 ) {
        for (var i = 0; i < arrayJSON.length; i++) {
            // console.log("value: " + arrayJSON[i].value);
            selectBox.append($("<option></option>").attr("value", arrayJSON[i].value).text(arrayJSON[i].name));
        }
    } else {
        selectBox.append($("<option></option>").attr("value", -2).prop("selected").text("Нет данных для выгрузки"));
    }

    $('#view-modal-window').fadeIn(300);
    selectBox.fadeIn(50);
    if ($('.hide-layout').css('display') != 'none') {
        $('.hide-layout').css("z-index", 1001);
    }
    showLayoutHide();
};
function hideModalWindow() {
    $('#view-modal-window').fadeOut(300);
    if ($('.hide-layout').css('z-index') === '1001') {
        $('.hide-layout').css("z-index", 998);
    }
    hideLayoutHide();
};
function getHouseList(commandID) {
    tempCommandID = commandID;
    sendMessage("getHouseAddedGisJkh");
};
function answerNo() {
    hideModalWindow();

    var selectBox = $('#house-select');
    if (selectBox.css('display') != 'none') {
        $('#house-select').empty();
        selectBox.fadeOut(300);
    } else {
        sendMessageWithParam("callQuestion", "false");
    }
};
function answerYes() {
    hideModalWindow();
    var selectBox = $('#house-select');
    if (selectBox.length > 0 && selectBox.css('display') != 'none') {
        // console.log($('#house-select option:selected').attr("value"));
        sendMessageWithParam(tempCommandID, $('#house-select option:selected').attr("value"));
        $('#house-select').empty();
        selectBox.fadeOut(300);
    } else {
        sendMessageWithParam("callQuestion", "true");
    }
};

