<%--
  Created by IntelliJ IDEA.
  User: 高大泽
  Date: 2022/2/22
  Time: 19:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户信息修改</title>
    <script>
        function $(id) {
            return document.getElementById(id);
        }
        function check() {
            var ouserName = $("userName");
            if(ouserName.value==''){
                alert("用户名不能为空！");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<form action="${pageContext.request.contextPath}/MainServlet?cmd=update" method="post" onsubmit="return check()">
    <%--隐藏域： 能当做表单元素来提交使用，但是页面上是不显示它的--%>
    <input type="hidden" name="id" value="${userDemo.id}">
    <table border="1" cellspacing="0" cellpadding="8">
        <tr>
            <th colspan="2" scope="col">用户信息修改</th>
        </tr>
        <tr>
            <td>用户名</td>
            <td><input name="userName" type="text" id="userName" value="${userDemo.userName}"/></td>
        </tr>
        <tr>
            <td>密码</td>
            <td><input name="userPwd" type="password" id="userPwd" value="${userDemo.userPwd}" /></td>
        </tr>
        <tr>
            <td>确认密码</td>
            <td><input name="reUserPwd" type="password" id="reUserPwd" /></td>
        </tr>
        <tr>
            <td>性别</td>

            <td><input type="radio" name="sex" value="男" <c:if test="${userDemo.sex == '男'}">checked</c:if>/> 男 <input
                    type="radio" name="sex" value="女" <c:if test="${userDemo.sex == '女'}">checked</c:if> /> 女</td>
        </tr>
        <tr>
            <td>爱好</td>
            <td><input name="chk" type="checkbox" id="chk" value="书法" />
                书法 <input name="chk" type="checkbox" id="chk" value="舞蹈" /> 舞蹈 <input
                        name="chk" type="checkbox" id="chk" value="音乐" /> 音乐 <input
                        name="chk" type="checkbox" id="chk" value="绘画" /> 绘画 <input
                        name="chk" type="checkbox" id="chk" value="游泳" /> 游泳</td>
        </tr>
        <tr>
            <td>学历</td>
            <td><select name="sel" id="sel">
                <option value="-1" selected="selected">====请选择====</option>
                <c:forEach items="${educationList}" var="education">
                    <option value="${education.eid}"
                        <%--如果回显的查询条件中的eid和下拉框数据中的eid一样--%>
                            <c:if test="${userDemo.sel == education.eid }">selected</c:if>
                    >${education.ename}</option>
                </c:forEach>
            </select></td>
        </tr>
        <tr>
            <td>家庭住址</td>
            <td><textarea name="address" cols="50" rows="10" id="address">${userDemo.address}</textarea></td>
        </tr>
        <tr>
            <td>生日</td>
            <td><input type="date" name="birthday" id="birthday"value="${userDemo.birthday}"></td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td><input type="submit" name="Submit" value="提交信息" /> <input
                    type="reset" name="Submit2" value="重置" /></td>
        </tr>
    </table>
</form>
</body>
</html>
