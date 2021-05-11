<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <#include "base.ftl" />
    <#import "spring.ftl" as spring />
</head>
<body>
<@spring.message "index.tip"/>
<div class="container">
    <button type="button" class="btn btn-primary" onclick="sessionTimeOut()">测试Ajax请求Session超时</button>
    <button type="button" class="btn btn-info" onclick="myOrder()">我的订单</button>
    <button type="button" class="btn btn-dark" onclick="permissionTest()">权限测试</button>
    <button type="button" class="btn btn-success">当前在线人数：${activeSessions!}</button>
    <button type="button" class="btn btn-success" onclick="logout()">退出</button>
    <button type="button" class="btn btn-success" onclick="system()">system测试</button>
</div>
</body>
<script type="text/javascript">
    function logout() {
        window.location = 'logout';
    }

    function system() {
        window.open('http://127.0.0.1:8082/system/systemToken?token=' + window.localStorage.token)
    }

    function sessionTimeOut() {
        $.post('check', function (result) {
            layer.alert(result.errMsg, {icon: 1});
        });
    }

    function myOrder() {
        window.location = 'order/myOrder';
    }

    function permissionTest() {
        window.location = 'permissionTest';
    }
</script>
</html>