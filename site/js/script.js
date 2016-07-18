/**
 * for index.html.
 */
$(function () {
    alignCenter($('.show-state'));
    alignCenter($('.error-list'));
    $(window).resize(function () {
        alignCenter($('.show-state'));
        alignCenter($('.error-list'));
        footerTop();
    });
    $('.wrapper').hover(function () {
        $(this).children().stop().animate({marginTop: '80%', opacity: 0}, 600);
    }, function () {
        $(this).children().stop().animate({marginTop: '0%', opacity: 1}, 600);
    });
    $('.settings').hover(function () {
        $(this).css({
            "box-shadow": "0 5px 20px 8px rgba(0,0,0,0.3)",
            "-moz-box-shadow": "0 5px 20px 8px rgba(0,0,0,0.3)",
            "-webkit-box-shadow": "0 5px 20px 8px rgba(0,0,0,0.3)"
        });
    }, function () {
        $(this).css({
            "box-shadow": "0 5px 20px 8px rgba(255,255,255,1)",
            "-moz-box-shadow": "0 5px 20px 8px rgba(255,255,255,1)",
            "-webkit-box-shadow": "0 5px 20px 8px rgba(255,255,255,1)"
        });
    });
    $('.button-state').click(function () {
        hideState();
        $(this).hide();
        setTimeout(function () {
            clearLabelText();
        }, 500);
        // $('#messages').val("");
    }).hide();

    $('.close-dialog').click(function () {
        hideErrorList();
    });
    $('.show-report').click(function () {
        showErrorList();
    });
    $('.thumb').click(function () { // вызов отправки операции
        var message = $(this).attr("id");
        if (message === undefined) {
            //    Доработать вывод сообщения "модуль в разработке"
        } else if (message === "exit") {
            exitRequest();
        } else {
            console.log(message);
            sendMessage(message);
        }
    });
    footerTop();
});
function exitRequest() {
    $.ajax({
        method: "DELETE",
        url: "/login"
    }).complete(function (answer) {
        ws.close();
        document.location.href = '/login';
        console.log(answer.responseText);
    });
};
function showState() {
    $('.hide-layout, .show-state').fadeIn(300);
};
function hideState() {
    $('.hide-layout, .show-state').fadeOut(300);
};
function showErrorList() {
    $('.hide-layout, .error-list').fadeIn(300);
};
function hideErrorList() {
    $('.error-list').fadeOut(300);
    hideLayoutHide();
};
function hideLayoutHide() {
    if ($('.show-state').css('display') === 'none') {
        $('.hide-layout').fadeOut(300);
    }
};
// функция принимает элемент, который необходимо центрировать
function alignCenter(elem) {
    elem.css({ // назначение координат left и top
        left: ($(window).width() - elem.width()) / 2 + 'px',
        top: ($(window).height() - elem.height()) / 2 + 'px'
    });
};
function footerTop() {
    var temp = 30;
    if ($(window).height() > 800) {
        temp = $(window).height() - 750 - parseInt($('.footer').height()) + 30;
    } else {
        temp = 30;
    }
    $('.footer').css({marginTop: temp + 'px'});
};
function showButton() {
    $('.button-state').show();
}
