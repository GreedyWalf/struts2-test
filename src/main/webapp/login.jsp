<%--
  Created by IntelliJ IDEA.
  User: qinyupeng
  Date: 2018/7/29
  Time: 下午5:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="jquery-1.11.2.js"></script>
<html>
<head>
    <title>登录页面</title>
</head>
<body>
<div>
    <form id="loginForm" action="login/login.login.do">
        用户名：<input type="text" name="userName"/><br>
        密码：<input type="password" name="password"/><br>
        <input type="submit" value="登录">
    </form>

    <img id="aaa" src="" alt="电子签购单">
</div>
</body>
<script>
    $(function () {
//        alert("aaa");
        $.post("login/login.getTxnElecImg.do", {}, function (data) {
            console.log(data);
            var msg = decodeURI(data.bytes);
            console.log(msg);
            $("#aaa").attr("src", "data:image/bmp;base64," + msg);
        });
    })

</script>
</html>
