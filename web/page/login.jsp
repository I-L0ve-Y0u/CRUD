<%--
  Created by IntelliJ IDEA.
  User: 高大泽
  Date: 2022/2/17
  Time: 16:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>用户登录</title>
    <link href="${pageContext.request.contextPath}/static/bootstrap.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/static/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/static/jquery-3.4.1.js"></script>
    <style>
        img {
            /*表示显示手型*/
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>用户登录</h1>

    <form action="${pageContext.request.contextPath}/MainServlet?cmd=LoginServlet" method="post" class="form-horizontal">

        <div class="form-group">
            <label for="username" class="col-md-2 control-label"> 用户名</label>
            <div class="col-md-3">
                <input type="text" name="username" id="username" class="form-control" placeholder="请输入用户名">
            </div>

        </div>
        <div class="form-group">
            <label for="userpwd" class="col-md-2 control-label"> 密码</label>
            <div class="col-md-3">
                <input type="password" name="userpwd" id="userpwd" class="form-control" placeholder="请输入用户名">
            </div>

        </div>
        <div class="form-group">
            <label for="loginCode" class="col-md-2 control-label"> 验证码</label>
            <div class="col-md-3">
                <input type="text" name="loginCode" id="loginCode" autocomplete="off" class="form-control"
                       placeholder="请输入验证码">
                <br>
                <img src="${pageContext.request.contextPath}/checkcode" id="img" alt="not find ?" onclick="checkImg()">
                <a href="#" id="changeImg" onclick="checkImgs()">看不清楚,换一张</a>
            </div>
            <div style="align-content: center;color: #0000ff" >${msg}</div>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-danger">登录</button>
        </div>

        <%--  提示信息--%>
        <%--<span style="align-content: center;color: #0000ff"><%=request.getAttribute("msg")%></span>--%>


    </form>

    <br><br>
    小老板，还没注册？<a href="${pageContext.request.contextPath}/MainServlet?cmd=RegisterServlet">立即注册</a>

</div>




<script>
    function checkImg() {
        //window.location.href ="Login_Register/CheckCodeServlet";
        var date = new Date();
        var elementById = document.getElementById("img");
        elementById.src = "${pageContext.request.contextPath}/checkcode?date" + date.getTime();

    }

    function checkImgs() {
        let date = new Date();
        let elementById = document.getElementById("img");
        elementById.src = "${pageContext.request.contextPath}/checkcode?date" + date.getTime();
    }


</script>

<c:if test="${param.msg != null and param.msg.equals('error')}">
    <script>
        alert("用户名或密码输入错误")
    </script>
</c:if>
<c:if test="${param.loginCode == null or param.loginCode.equals('')}">
    <script>
        alert("请输入验证码")
    </script>

</c:if>


<%--<%
    //获取传递的get请求的值（通过之前url传递的）
    String msg = request.getParameter("msg");
    String loginCode =  request.getParameter("loginCode");
    if (msg != null && msg.equals("error")) {
%>
<script>
    alert("用户名或密码输入错误")
</script>
<%
    }
    if (loginCode == null || loginCode.equals("")){
        %>
<script>
    alert("请输入验证码")
</script>
<%

    }

%>--%>

<!--   -->

</body>
</html>
