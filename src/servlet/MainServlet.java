package servlet;

import com.alibaba.fastjson.JSONObject;
import entity.Condition;
import entity.Education;
import entity.UserDemo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.junit.Test;
import service.EducationService;
import service.EducationServiceImpl;
import service.UserService;
import service.UserServiceImpl;
import utils.PageBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebServlet(name = "MainServlet", urlPatterns = "/MainServlet")
public class MainServlet extends HttpServlet {
    UserService userService = new UserServiceImpl();
    EducationService educationService = new EducationServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String cmd = request.getParameter("cmd");
        if (cmd != null && "LoginServlet".equals(cmd)) {
            //登录
            login(request, response);
        } else if (cmd != null && "DelSessionServlet".equals(cmd)) {
            //删除登录（退出）
            delSession(request, response);
        } else if (cmd != null && "RegisterServlet".equals(cmd)) {
            //注册数据
            register(request, response);
        } else if (cmd != null && "DeleteServlet".equals(cmd)) {
            //删除数据
            del(request, response);
        } else if (cmd != null && "MainServlet".equals(cmd)) {
            //主页显示
            main(request, response);
        } else if (cmd != null && "updateUI".equals(cmd)) {
            //数据回显
            updateUI(request, response);
        } else if (cmd != null && "update".equals(cmd)) {
            //修改数据
            update(request, response);
        }else if(cmd != null && "loginUI".equals(cmd)){
            // 在跳转页面时 需要携带数据到login页面 所以使用学的方法取获取session会话得到数据（学历的） 在重定向到注册页面
            loginUI(request,response);
        }
        else if(cmd != null && "registerUI".equals(cmd)){
            // 在跳转页面，直接跳转register，jsp页面时 无法显示学历 所以同上loginUI 在访问页面同时获取session 在重定向到注册页面
            registerUI(request,response);
        }else if (cmd != null && "existsUserName".equals(cmd)){
            existsUserName(request,response);
        }
    }


    private void existsUserName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        System.out.println("userName = " + userName);

        JSONObject json = new JSONObject();
        boolean b = userService.existsUserName(userName);
        if (b) {
            json.put("msg","success");
        }else {
            json.put("msg","fail");
        }
        response.getWriter().write(json.toJSONString());
    }


    private void registerUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Education> educationList = educationService.findAllEducation();
        //选择session域对象的原因 以为查询，删除，更改 都需要学历下拉框 所以保持session对话
        request.getSession().setAttribute("educationList", educationList);
        //重定向到register.jsp(注册页面)
        response.sendRedirect(request.getContextPath()+"/page/register.jsp");
    }

    private void loginUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //查询出学历信息，保存到域对象中，回显到页面上
        List<Education> educationList = educationService.findAllEducation();
        //选择session域对象的原因 以为查询，删除，更改 都需要学历下拉框 所以保持session对话
        request.getSession().setAttribute("educationList", educationList);
        //重定向到login.jsp(登录页面)
        response.sendRedirect(request.getContextPath()+"/page/login.jsp");
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //  取（1）
        String[] chks = request.getParameterValues("chk");

        Map<String, String[]> parameterMap = request.getParameterMap();
        //Set<String> set = parameterMap.keySet();
        UserDemo userDemo = new UserDemo();
        //注意，所有从前端传递的信息都是String类型的 包括日期
        //所以日期如果实体是Data类型，则无法转换（会报错 Dateconverter），需要处理
        //使用日期转换器
        DateConverter dateConverter = new DateConverter();
        dateConverter.setPatterns(new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"});
        ConvertUtils.register(dateConverter, Date.class);
       /* for (String s : set) {
            String[] strings = parameterMap.get(set);
        }*/
        try {
            BeanUtils.populate(userDemo, parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        userDemo.setChk(Arrays.toString(chks));

//目前去执行会报 500 的原因是以为只导入了beamutils报需要以来logging包

        //注意，所有从前端传递的信息都是String类型的 包括日期
        //所以日期如果实体是Data类型，则无法转换（会报错 Dateconverter），需要处理
        //使用日期转换器

//此时 userDemo对象的数值已经传到位 id也在页面上的隐藏域 传进来了
        //  调 （2）
        //UserService userService = new UserServiceImpl();

        boolean b = userService.updateUserByID(userDemo);
        // 存（3）
        if (b) {
            request.setAttribute("update", "success");

        } else {
            request.setAttribute("update", "fail");
        }
        // 转（4）
        main(request, response);
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String userpwd = request.getParameter("userpwd");
        //要实现得到的验证码与页面提供的验证码的比较
        String loginCode = request.getParameter("loginCode");
        Object vsCode = request.getSession(false).getAttribute("vsCode");
        //首先判断是否得到了拼接好的验证码
        if (vsCode == null) {
            //通过创建域对象去保存错误需要提示的信息，方便可以在页面上显示
            request.setAttribute("msg", "服务器未接收到验证码，获取错误！！");
            //因为有数据需要携带，所以就使用转发！
            request.getRequestDispatcher("/page/login.jsp").forward(request, response);
            //既然没有得到验证码，返回，不执行下面的程序
            return;
        }
        //再次判断是否验证码输入正确（这里判断错误的情况，节省代码）
        if (!loginCode.equalsIgnoreCase((String) vsCode)) {
            //通过创建域对象去保存错误需要提示的信息，方便可以在页面上显示
            request.setAttribute("msg", "验证码错误");
            //因为有数据需要携带，所以就使用转发！
            request.getRequestDispatcher("/page/login.jsp").forward(request, response);
            return;
        }



        //UserService userService = new UserServiceImpl();
        try {
            boolean b = userService.inquireUser(username, userpwd);
            if (b) {
                request.getSession().setAttribute("username", username);
                //转发与重定向都可以 要满足的是要跳转到到成功的页面
                request.getRequestDispatcher("/MainServlet?cmd=MainServlet").forward(request, response);
            } else {
                //不成功，再次跳转到登录页面

                response.sendRedirect(request.getContextPath() + "/page/login.jsp?msg=error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        // 判断，如果这里的session超过了有效期，会变成空，运行会报空指针异常，所以判断是否为空
        if (session != null) {
            session.removeAttribute("username");
        }
        response.sendRedirect(request.getContextPath() + "/page/login.jsp");
    }

    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String username = request.getParameter("userName");
        //String userpwd = request.getParameter("userpwd");
        String[] chks = request.getParameterValues("chk");

        Map<String, String[]> parameterMap = request.getParameterMap();
        //Set<String> set = parameterMap.keySet();
        UserDemo userDemo = new UserDemo();
        //注意，所有从前端传递的信息都是String类型的 包括日期
        //所以日期如果实体是Data类型，则无法转换（会报错 Dateconverter），需要处理
        //使用日期转换器
        DateConverter dateConverter = new DateConverter();
        dateConverter.setPatterns(new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"});
        ConvertUtils.register(dateConverter, Date.class);
       /* for (String s : set) {
            String[] strings = parameterMap.get(set);
        }*/
        try {
            BeanUtils.populate(userDemo, parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        userDemo.setChk(Arrays.toString(chks));
//目前去执行会报 500 的原因是以为只导入了beamutils报需要以来logging包

        //注意，所有从前端传递的信息都是String类型的 包括日期
        //所以日期如果实体是Data类型，则无法转换（会报错 Dateconverter），需要处理
        //使用日期转换器


        try {
          boolean  bool = userService.addUser(userDemo);
            if (bool) {
                //重定向 响应弹到登录页面
                response.getWriter().write("<h1>恭喜你，注册成功，3秒后将跳转到登陆页面！！</h1>");
                //response.sendRedirect(request.getContextPath()+"/login.jsp");
                //refresh请求头，表示xxx秒后跳转到xxx哥页面
                response.setHeader("refresh", "3;url=" + request.getContextPath() + "/page/login.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/page/error.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //  取
        String id = request.getParameter("id");
        System.out.println(id);
        boolean b = userService.delUser(Integer.valueOf(id));

        if (b) {
            request.setAttribute("delete", "success");

        } else {
            request.setAttribute("delete", "fail");
        }
        main(request, response);
    }

    public void main(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        Condition condition = new Condition();
        try {
            BeanUtils.populate(condition, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

            //List<UserDemo> allUser = userService.findUserDemoBySome(condition);
            //注意： 如果要在jsp中显示数据，那么要把list放到域对象中，传到jsp中

            // 获取到 前端点击是p（当前页数）数据
            String strP = request.getParameter("p");
            //转换为Integer类型 用于后端计算
// 初始化 p 当前页数为1（如果此时的p是空的，则让他显示第一页的数据）
            Integer p = 1;
            if (strP != null && !strP.isEmpty()){
                p = Integer.valueOf(strP);
            }
            PageBean pageBean = userService.findUserDemoByPageBean(condition,p);
            //再把返回的pageBean对象回显到页面上
            request.setAttribute("pageBean",pageBean);


            HttpSession session = request.getSession(false);
            Object username = session.getAttribute("username");
            if (username == null) {
                response.getWriter().write("<h1>抱歉，您还没登录呦！系统将会在5秒内返回登录！</h1>");
                response.setHeader("refresh", "5;url=" + request.getContextPath() + "/page/login.jsp");
                return;
            }

            //request.setAttribute("allUser", allUser);

            //把取到查询条件，再次放到域对象中，回显到页面上
            request.setAttribute("condition",condition);
            request.getRequestDispatcher("/page/element.jsp").forward(request, response);
            //一下为没有书写jsp的解决办法（繁琐）
           /* PrintWriter writer = response.getWriter();
            writer.write("<h1>用户列表</h1>");
            writer.write("<table width = 800 border = 1 >");
            writer.write("<tr>" + "<th>编号</th>" +
                    "<th>用户名</th>" +
                    "<th>密码</th>" +
                    "<th>性别</th>" +
                    "<th>爱好</th>" +
                    "<th>学历</th>" +
                    "<th>地址</th>" +
                    "<th>生日</th>" +
                    "<th>操作</th>" + "</tr>");
            for (UserDemo userDemo : allUser) {
                writer.write("<tr>");
                writer.write("<td>" + userDemo.getId() + "</td>");
                writer.write("<td>" + userDemo.getUserName() + "</td>");
                writer.write("<td>" + userDemo.getUserPwd() + "</td>");
                writer.write("<td>" + userDemo.getSex() + "</td>");
                writer.write("<td>" + userDemo.getChk() + "</td>");
                writer.write("<td>" + userDemo.getSel() + "</td>");
                writer.write("<td>" + userDemo.getAddress() + "</td>");
                writer.write("<td>" + userDemo.getBirthday() + "</td>");
                writer.write("<td><a href = '/Login_Register/DeleteServlet?id="+userDemo.getId()+"'> 删除</a></td> ");
                writer.write("</tr>");
            }
            writer.write("</table>");
            writer.write("<a href='<%=%>'");*/


    }


    public void updateUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //取  调  存  转
        //取 - 前端传递的数据
        String id = request.getParameter("id");
        //根据id获取当前用户的信息，回显到修改页面上
        //调 - 业务层
        UserDemo userDemo = userService.getUserByID(Integer.valueOf(id));
        //存 - 存到域对象
        request.setAttribute("userDemo", userDemo);
        //转 - 转发到页面
        request.getRequestDispatcher("/page/update.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Test
    public void test01(){
        PageBean pageBean = userService.findUserDemoByPageBean(new Condition(), 2);
        List<UserDemo> dataList = pageBean.getDataList();
        for (UserDemo userDemo : dataList) {
            System.out.println("userDemo = " + userDemo);
        }
        System.out.println("pageBean.getPageSize() = " + pageBean.getPageSize());
        System.out.println("pageBean.getPageCount() = " + pageBean.getPageCount());
        System.out.println("pageBean.getPageTotal() = " + pageBean.getPageTotal());
        System.out.println("pageBean.getP() = " + pageBean.getP());

    }
}
