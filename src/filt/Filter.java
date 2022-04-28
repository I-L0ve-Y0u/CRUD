package filt;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "Filter",urlPatterns = "/index2.jsp",dispatcherTypes = {DispatcherType.FORWARD})
public class Filter implements javax.servlet.Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        // 调用方法 放行请求（doFilter（请求对象，响应对象））
        chain.doFilter(req, resp);
        //chain —— 过滤链对象

    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("HelloFilter ---- 完成初始化");

    }

}
