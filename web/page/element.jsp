<%@ page import="java.util.List" %>
<%@ page import="entity.UserDemo" %><%--
  Created by IntelliJ IDEA.
  User: 高大泽
  Date: 2022/2/17
  Time: 16:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>用户数据</title>
    <link href="${pageContext.request.contextPath}/static/bootstrap.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/static/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/static/jquery-3.4.1.js"></script>
</head>
<style>
    table {

        border-color: rosybrown;
        width: 800px;

    }
</style>
<body>
<div class="container">
    <h1> 用户数据 </h1>
    欢迎您：
    <span style="color: white ;background-color: blue ">
    <%=session.getAttribute("username")%>
    </span>
    <a href="${pageContext.request.contextPath}/MainServlet?cmd=DelSessionServlet" role="button">退出</a>
    <br><br>

    <%-- 添加查询条件--%>
    <form action="${pageContext.request.contextPath}/MainServlet?cmd=MainServlet" method="post" name="searchForm" class="form-inline">
        <input type="hidden" name="p" id="p">
        <div class="form-group">
            <label for="p" class="control-label">姓名：</label>
            <input type="text" name="searchUserName" value="${condition.searchUserName}" class="form-control">
        </div>
         &nbsp;&nbsp;
        <div class="form-group">
            <label for="p" class="control-label">学历：</label>
            <select name="eid" class="form-control" >
                <option value="">====请选择====</option>

                <c:forEach items="${educationList}" var="education">
                    <option value="${education.eid}"
                        <%--如果回显的查询条件中的eid和下拉框数据中的eid一样--%>
                            <c:if test="${condition.eid == education.eid }">selected</c:if>
                    >${education.ename}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="startDate">开始时间</label>
            <input type="date" name="startDate" id="startDate" class="form-control" value="${condition.startDate}">
        </div>
        <div class="form-group">
            <label for="endDate">结束时间</label>
            <input type="date" name="endDate" id="endDate" class="form-control" value="${condition.endDate}">
        </div>
        <button type="submit" class="btn btn-primary">查询</button>

    </form>

</div>


<div class="container">
    <table class="table table-bordered table-hover">
        <thead>
        <tr>
            <th>编号</th>
            <th>用户名</th>
            <th>密码</th>
            <th>性别</th>
            <th>爱好</th>
            <th>学历</th>
            <th>地址</th>
            <th>生日</th>
            <th>操作</th>
        </tr>

        </thead>
        <tbody>
        <c:forEach items="${pageBean.dataList}" var="userDemo" varStatus="vs">

       <tr <%-- <c:if test="${vs.index%2==0}"> bgcolor="#cd5c5c" </c:if>--%>
        >
            <td>${userDemo.id} </td>
            <td>${userDemo.userName} </td>
            <td>${userDemo.userPwd} </td>
            <td>${userDemo.sex} </td>
            <td>${userDemo.chk} </td>
            <td>${userDemo.ename} </td>
            <td>${userDemo.address} </td>
            <td>${userDemo.birthday} </td>
            <td><a href='javascript:del(${userDemo.id})' role="button"> 删除</a> |
                <a href='javascript:update(${userDemo.id})' role="button"> 修改</a>
            </td>
        </tr>

        </c:forEach>
        </tbody>
    </table>
    <br><br>
    <p>共${pageBean.pageTotal}条记录，第${pageBean.p}页/共${pageBean.pageCount}页 &nbsp;&nbsp;&nbsp;
        <a href="javascript:toPage(1)">首页</a>
        <a href="javascript:toPage(${pageBean.p-1})">上一页</a>
        <a href="javascript:toPage(${pageBean.p+1})">下一页</a>
        <a href="javascript:toPage(${pageBean.pageCount})">尾页</a>
    </p>

    <br><br>
    <a href="${pageContext.request.contextPath}/page/login.jsp" role="button"> >>返回登录</a>
</div>



    <script>
        function del(id) {
            if (confirm("是否确定删除？")) {
                window.location.href = "${pageContext.request.contextPath}/MainServlet?cmd=DeleteServlet&id=" + id;
            }
        }

        function update(id) {
            window.location.href = "${pageContext.request.contextPath}/MainServlet?cmd=updateUI&id=" + id;
        }


        function toPage(p) {
            //把当前页p和查询条件一起提交到servlet中的main方法中
            //赋值
            $("#p").val(p);
            //1.给form表单添加name属性
            //2.document.name属性的名称.submit();
            //提交表单 - 就跟点击了【查询】是一样的效果
            document.searchForm.submit();
        }
    </script>

    <script>
        <c:if test="${delete == 'success'}">
        alert("删除成功")
        </c:if>
        <c:if test="${delete == 'fail'}">
        alert("删除失败")
        </c:if>
        <c:if test="${update == 'success'}">
        alert("更新成功")
        </c:if>
        <c:if test="${update== 'fail'}">
        alert("更新失败")
        </c:if>
    </script>
    <%--<%
       List<UserDemo> allUser = (List) request.getAttribute("allUser");
        for (UserDemo userDemo : allUser) {
            %>
    <tr>
        <td><%=userDemo.getId()%> </td>
        <td><%=userDemo.getUserName()%> </td>
        <td><%=userDemo.getUserPwd()%> </td>
        <td><%=userDemo.getSex()%> </td>
        <td><%=userDemo.getChk()%> </td>
        <td><%=userDemo.getSel()%> </td>
        <td><%=userDemo.getAddress()%> </td>
        <td><%=userDemo.getBirthday()%> </td>
        <td><a href = '<%=request.getContextPath()%>/DeleteServlet?id=<%=userDemo.getId()%>'> 删除</a></td>
    </tr>
    <%
        }
    %>--%>
    >

</body>
</html>
