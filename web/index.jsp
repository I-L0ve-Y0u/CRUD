<%--
  Created by IntelliJ IDEA.
  User: 高大泽
  Date: 2022/2/22
  Time: 14:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
    <style>
      div{
        align: center;
      }
    </style>
  </head>

  <body>
  <div>
  <h1>欢迎使用！！</h1>
  <a href="${pageContext.request.contextPath}/MainServlet?cmd=registerUI" >立即注册</a>
  <br><br>
  <a href="${pageContext.request.contextPath}/MainServlet?cmd=loginUI">已有账号，立即登录</a>
  </div>
  </body>
</html>
