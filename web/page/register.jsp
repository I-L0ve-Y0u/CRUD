<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 高大泽
  Date: 2022/2/17
  Time: 16:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>注册用户信息</title>
    <%--<script>
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
    </script>--%>
    <script>

        var isFlag = false;
        function check() {
            if (!isFlag){
                return true

            }
            return false
        }

    </script>
    <script src="${pageContext.request.contextPath}/static/jquery-3.4.1.js"></script>
    <script>
        $(function (){
            console.log(11111);
            //获取用户名文本框的对象
            //给文本框绑定失去焦点事件
            $("#userName").blur(function (){
                console.log(22222);

                //获取文本框对象的值
                var $userName = $("#userName").val();
                //发送异步请求，把用户名发送到后端，进行验证
                /**
                 * url : 表示异步请求请求到的后端的地址
                 * async: 表示当前请求是异步（false）或者是同步（true）
                 * dataType: 表示后端响应的数据的格式：json, text
                 * data: 前端准备发送到后端的数据
                 * type: 表示当请求的方式：get / post
                 * success: 表示响应成功之后执行的回调函数
                 * 回调函数 : 接收后端响应的数据
                 * function(data): 响应回来的数据
                 * error: 表示请求失败的响应
                 *
                 * {"name":"value"}
                 */
                $.ajax({
                    url:"${pageContext.request.contextPath}/MainServlet?cmd=existsUserName",
                    async:false,
                    dataType:"json",
                    data:{"userName":$userName},
                    type:"get",
                    success:function (data) {
                        /*data就是json对象
                        * 判断如果用户存在，不能注册{"msg":"fail"}
                        * 如果用户不存在，可以注册{"msg":"success"}*/
                        if (data.msg == 'success'){
                            $("#sp").html("恭喜，该用户名可以使用！");
                            $("#sp").css("color","green");
                            isFlag = true;

                        }else if (data.msg == 'fail'){
                            $("#sp").html("用户名存在，不可注册！");
                            $("#sp").css("color","red");
                            isFlag = false;
                        }


                    },
                    error:function (data) {
                        $("#sp").html("服务器访问失败！");
                        $("#sp").css("color","red");
                    }
                })

            });


        })

    </script>
</head>
<body>
<form action="${pageContext.request.contextPath}/MainServlet?cmd=RegisterServlet" method="post" onsubmit="return check()">
    <table border="1" cellspacing="0" cellpadding="8">
        <tr>
            <th colspan="2" scope="col">用户信息注册</th>
        </tr>
        <tr>
            <td>用户名</td>
            <td><input name="userName" type="text" id="userName" />&nbsp;&nbsp;&nbsp;
                <%--用来进行用户名是否可用的提示--%>
                <span id="sp"></span>
            </td>
        </tr>
        <tr>
            <td>密码</td>
            <td><input name="userPwd" type="password" id="userPwd" /></td>
        </tr>
        <tr>
            <td>确认密码</td>
            <td><input name="reUserPwd" type="password" id="reUserPwd" /></td>
        </tr>
        <tr>
            <td>性别</td>
            <td><input type="radio" name="sex" value="男" checked/> 男 <input
                    type="radio" name="sex" value="女" /> 女</td>
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
                    <option value="${education.eid}">${education.ename}</option>
                </c:forEach>
            </select></td>
        </tr>
        <tr>
            <td>家庭住址</td>
            <td><textarea name="address" cols="50" rows="10" id="address"></textarea></td>
        </tr>
        <tr>
            <td>生日</td>
            <td><input type="date" name="birthday" id="birthday"></td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td><input type="submit" name="Submit" value="提交信息" onsubmit="return check()" /> <input
                    type="reset" name="Submit2" value="重置" /></td>
        </tr>
    </table>
</form>

</body>
</html>
