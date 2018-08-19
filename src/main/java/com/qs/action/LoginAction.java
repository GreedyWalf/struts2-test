package com.qs.action;

import com.qs.common.base.BaseAction;
import com.qs.common.utils.JsonUtil;
import com.qs.entity.User;
import com.qs.model.UserModel;
import com.qs.service.UserService;

import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginAction extends BaseAction<UserModel> {

    //service的实例创建交给spring容器去维护，需要提供get、set方法
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserService getUserService() {
        return userService;
    }


    /**
     * 跳转到登录页面（页面跳转）
     */
    public String login() {
        //提供BaseAction中指定userModel，绑定客户端传递参数到model对象中。
        String userName = model.getUserName();
        String password = model.getPassword();
        System.out.println("userName=" + userName);
        System.out.println("password=" + password);
        //如果登录成功，跳转到首页
        if ("admin".equals(userName) && "000000".equals(password)) {
            //将登录成功的用户信息存在session中
            HttpSession session = request.getSession();
            if (session == null) {
                session = request.getSession(true);
            }

            User user = new User();
            user.setUserName(userName);
            user.setUserId("1111");
            session.setAttribute("userInfo", user);
            return "index";
        }

        //约定下，假如request中可以获取到ssoUserName=zhangsan,则进行免登陆
        String ssoLogin = request.getParameter("ssoLogin");
        if("true".equals(ssoLogin)){
            return "index";
        }

        return render("login");
    }

    /**
     * 返回json格式字符串（ajax请求时使用）
     */
    public String getUser() {
        User user = userService.getUserByUserId("1111");
        return renderJson(user);
    }

    //上面的renderJson方法，内部实现如下：其实就是依靠的response响应流的方式，将对象转换为json字符串，从而返回到客户端
    public String testJson() {
        User user = userService.getUserByUserId("1111");
        try {
            String json = JsonUtil.obj2Json(user, false);
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(json);
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String error(){
        return "error";
    }
}
