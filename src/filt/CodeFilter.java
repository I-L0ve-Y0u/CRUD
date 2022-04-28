package filt;

import javax.servlet.*;
import javax.servlet.Filter;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

//过滤器处理编码问题
@WebFilter(filterName = "CodeFilter",urlPatterns = "/*",
initParams = {@WebInitParam(name="encoding",value = "utf-8")})
public class CodeFilter implements Filter {

    //编码
    String code = "";

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding(code);
        resp.setContentType("text/html;charset="+code);
        System.out.println("CodeFilter...doFilter执行了...");
        //放行的方法
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        //config对象：可以获取配置参数
        code = config.getInitParameter("encoding");
    }

}
