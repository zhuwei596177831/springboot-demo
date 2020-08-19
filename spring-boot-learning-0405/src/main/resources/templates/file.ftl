<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>文件测试页面</title>
</head>
<body>
<form action="uploadFile" method="post" enctype="multipart/form-data">
    <#--<form action="uploadFile" method="post" enctype="application/x-www-form-urlencoded">-->
    <#--<form action="uploadFile" method="post" enctype="text/plain">-->
    姓名：<input type="text" name="name"><br>
    邮箱：<input type="text" name="email"><br>
    文件：<input type="file" name="testFile">
    <input type="submit" value="提交">
</form>
</body>
</html>