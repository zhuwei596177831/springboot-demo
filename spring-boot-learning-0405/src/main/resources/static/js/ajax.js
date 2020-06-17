$.ajaxSetup({
    contentType: 'application/x-www-form-urlencoded;charset=utf-8',
    complete: function (XMLHttpRequest, textStatus) {
        console.log('ajaxSetup起作用了');
        var sessionStatus = XMLHttpRequest.getResponseHeader('sessionStatus');
        if (sessionStatus == 'sessionTimeout') {
            layer.alert('登录超时,请重新登录', {icon: 5, closeBtn: 0}, function (index) {
                layer.close(index);
                window.location = 'login';
            });
        }
    }
});
