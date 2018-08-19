package com.qs.common.filter;

import com.qs.entity.User;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 定义过滤器，拦截所有请求，校验用户是否登录，对于没有登录的请求，跳转到登录页面
 */
public class SessionFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String uri = request.getRequestURI();
        HttpSession session = request.getSession();
        User userInfo = null;
        if (session != null) {
            userInfo = (User) session.getAttribute("userInfo");
        }


        String ssoUserName = request.getParameter("ssoUserName");
        System.out.println("ssoUserName=" + ssoUserName);
        if (userInfo != null) {  //用户已经登录
            chain.doFilter(req, resp);
        } else if (StringUtils.isNotBlank(ssoUserName)) { //如果是单点登录
            request.getRequestDispatcher("transLogin.jsp").forward(request, response);
        } else if (uri.contains(".js") && !uri.contains(".jsp")) {
            chain.doFilter(req, resp);
        } else if (uri.contains("login.jsp")) {  //登录页面不拦截
            chain.doFilter(req, resp);
        } else {  //用户未登录
            if (uri.contains("login.do")) {
                chain.doFilter(req, resp);
                return;
            }

            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
