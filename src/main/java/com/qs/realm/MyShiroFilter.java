package com.qs.realm;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class MyShiroFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest req, ServletResponse res, Object o) throws Exception {
        Subject subject = getSubject(req, res);
        HttpServletRequest request = WebUtils.toHttp(req);
        HttpServletResponse response = WebUtils.toHttp(res);
        StringBuffer requestURL = request.getRequestURL();
        if (requestURL.toString().contains("login")) {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.write("<script>alert('aaaaa')</script>");
            out.flush();
            return false;
        }

        if(requestURL.toString().contains("index")){
            response.sendRedirect("http://www.baidu.com");
            return false;
        }

        //返回true等同于chain.doFilter(request,response)
        return true;
    }
}
