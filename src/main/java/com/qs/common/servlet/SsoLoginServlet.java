package com.qs.common.servlet;

import com.qs.entity.User;
import com.sun.deploy.net.HttpResponse;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SsoLoginServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("------>>>servlet执行啦~~~");
        String ssoUserName = request.getParameter("ssoUserName");
        if("zhangsan".equals(ssoUserName)){
            User user = new User();
            user.setUserName("zhangsan");
            HttpSession session = request.getSession();
            if(session == null){
                session = request.getSession(true);
            }

            session.setAttribute("userInfo",user);
            //登陆成功，跳转到首页
            response.sendRedirect("login/login.login.do?ssoLogin=true");
        }else{

        }


    }
}
