package filt;

import javax.servlet.*;
import javax.servlet.Filter;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * //配置拦截的方式：转发 - xml配置
 * <dispatcher>FORWARD</dispatcher>
 * 结果一样的注解配置：
 * DispatcherType.FORWARD
 *
 */
@WebFilter(filterName = "Filter4",urlPatterns = "/test01.jsp",
dispatcherTypes = {DispatcherType.FORWARD})
public class Filter4 implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("Filter4...doFilter执行了...");
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
