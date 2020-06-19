package com.qs.action;

import com.google.gson.Gson;
import com.qs.common.base.BaseAction;
import com.qs.common.utils.JsonUtil;
import com.qs.entity.User;
import com.qs.model.UserModel;
import com.qs.service.UserService;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
        if ("true".equals(ssoLogin)) {
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

    public String error() {
        return "error";
    }


    /**
     * 使用http协议发送请求和获取请求的响应；（使用HttpURLConnection实现）
     *
     * @since 2018年08月21日15:26:02
     * @author Qs
     */
    public void sendUrl() {
        String requestUrl = "http://localhost:8888/struts/login.getUser.do";
        User param = new User();
        param.setUserName("zhangsan");
        param.setUserId("1111");

        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            //设置请求方式
            connection.setRequestMethod("POST");
            //设置超时时间
            connection.setConnectTimeout(5000);
            connection.setRequestProperty("Content-Type", "application/json");

            Gson gson = new Gson();
            String sendParam = gson.toJson(param);
            System.out.println("sendParam=" + sendParam);

            byte[] paramBytes = sendParam.getBytes("UTF-8");
            connection.setRequestProperty("Content-Length", String.valueOf(paramBytes.length));

            //将请求传递的参数写入formData中进行传递
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(paramBytes);
            outputStream.flush();
            outputStream.close();

            //获取响应码
            int code = connection.getResponseCode();
            String resp = "";
            if (code == 200) {
                InputStream inputStream = connection.getInputStream();
                byte[] buff = new byte[1024];
                int len = 0;
                StringBuilder sb = new StringBuilder();
                while (-1 != (len = (inputStream.read(buff)))) {
                    sb.append(new String(buff, 0, len, "UTF-8"));
                }

                resp = sb.toString();
            }

            if (StringUtils.isNotBlank(resp)) {
                System.out.println("输出响应：" + resp);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
