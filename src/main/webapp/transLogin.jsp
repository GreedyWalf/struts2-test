<%@ page import="org.apache.commons.lang.StringUtils" %><%--
  Created by IntelliJ IDEA.
  User: qinyupeng
  Date: 2018/7/29
  Time: 下午11:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用于登录页面跳转</title>
</head>

<script type="text/javascript">
    try {
        <%
            String ssoUserName = request.getParameter("ssoUserName");
            if(StringUtils.isNotBlank(ssoUserName)){
                request.getRequestDispatcher("/ssoLogin").forward(request,response);
            }
        %>
        parent.parent.parent.location.href = '<%= request.getContextPath()%>/login.jsp';
    } catch (e) {
        try {
            parent.parent.location.href = '<%= request.getContextPath()%>/login.jsp';
        } catch (e) {
            try {
                parent.location.href = '<%= request.getContextPath()%>/login.jsp';
            } catch (e) {
                window.location.href = '<%= request.getContextPath()%>/login.jsp';
            }
        }
    }
</script>

<body>

</body>
</html>
