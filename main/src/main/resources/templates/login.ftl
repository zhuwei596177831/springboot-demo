<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <#include "base.ftl" />
    <#import "spring.ftl" as spring />
</head>
<body>
<div class="container">
    <form enctype="multipart/form-data" method="post" id="loginForm">
        <div class="form-group">
            <label for="exampleInputEmail1"><@spring.message code="email"/></label>
            <input type="email" class="form-control" id="loginId" name="loginId"
                   placeholder="<@spring.message code="email"/>">
        </div>
        <div class="form-group">
            <label for="exampleInputPassword1"><@spring.message "password"/></label>
            <input type="password" class="form-control" id="password" name="password"
                   placeholder="<@spring.message code="password"/>">
        </div>
        <div class="form-group">
            <input type="checkbox" name="rememberMe">记住我
        </div>
        <#--        <div class="form-group">-->
        <#--            <label for="exampleInputPassword1"><@spring.message "file"/></label>-->
        <#--            <input type="file" class="form-control" name="loginFile" multiple-->
        <#--                   placeholder="<@spring.message code="file"/>">-->
        <#--        </div>-->
    </form>
    <button type="button" class="btn btn-primary" onclick="language('zh_CN')">中文</button>
    <button type="button" class="btn btn-info" onclick="language('en_US')">英文</button>
    <button type="button" class="btn btn-success" onclick="login()">登录</button>
</div>
<script>
    $(function () {
        window.localStorage.locale = window.localStorage.locale == undefined ? 'zh_CN' : window.localStorage.locale;
    });

    function language(locale) {
        window.localStorage.locale = locale;
        window.location.href = 'login?locale=' + window.localStorage.locale;
    }

    function login() {
        var loginId = $('input[name="loginId"]').val();
        if (!loginId) {
            layer.alert("用户名必填", {icon: 0});
            return;
        }
        var password = $('input[name="password"]').val();
        if (!password) {
            layer.alert("密码必填", {icon: 0});
            return;
        }
        $.post('login', $('#loginForm').serialize(), function (result) {
            if (result.errStat !== 0) {
                layer.alert(result.errMsg, {icon: 2});
                return;
            }
            window.localStorage.token = result.data;
            window.location.href = 'index?token=' + result.data + '&locale=' + window.localStorage.locale;
        });
        // $('#loginForm').ajaxSubmit({
        //     url: 'login',
        //     data: $('#loginForm').serialize(),
        //     beforeSubmit: function () {
        //         var email = $('input[name="email"]').val();
        //         if (!email) {
        //             alert("邮箱必填");
        //             return false;
        //         }
        //         var password = $('input[name="password"]').val();
        //         if (!password) {
        //             alert("密码必填");
        //             return false;
        //         }
        //     },
        //     success: function (data) {
        //         if (data === '成功') {
        //             window.location.href = 'index?locale=' + window.localStorage.locale;
        //         }
        //     }
        // });
    }
</script>
</body>
</html>