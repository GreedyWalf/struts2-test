package com.qs.common.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class Struts2Utils {
    private static final String ENCODING_PREFIX = "encoding";
    private static final String NOCACHE_PREFIX = "no-cache";
    private static final String ENCODING_DEFAULT = "UTF-8";
    private static final boolean NOCACHE_DEFAULT = true;
    private static final String TEXT_TYPE = "text/plain";
    private static final String JSON_TYPE = "application/json";
    private static final String XML_TYPE = "text/xml";
    private static final String HTML_TYPE = "text/html";
    private static final String JS_TYPE = "text/javascript";
    private static Log logger = LogFactory.getLog(Struts2Utils.class);

    public Struts2Utils() {
    }

    public static HttpSession getSession() {
        return ServletActionContext.getRequest().getSession();
    }

    public static HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    public static HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }

    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    public static void render(String contentType, String content, String... headers) {
        try {
            String encoding = "UTF-8";
            boolean noCache = true;
            String[] arr$ = headers;
            int len$ = headers.length;

            for (int i$ = 0; i$ < len$; ++i$) {
                String header = arr$[i$];
                String headerName = StringUtils.substringBefore(header, ":");
                String headerValue = StringUtils.substringAfter(header, ":");
                if (StringUtils.equalsIgnoreCase(headerName, "encoding")) {
                    encoding = headerValue;
                } else {
                    if (!StringUtils.equalsIgnoreCase(headerName, "no-cache")) {
                        throw new IllegalArgumentException(headerName + "Is not a valid header type");
                    }

                    noCache = Boolean.parseBoolean(headerValue);
                }
            }

            HttpServletResponse response = ServletActionContext.getResponse();
            String fullContentType = contentType + ";charset=" + encoding;
            response.setContentType(fullContentType);
            if (noCache) {
                response.setHeader("Pragma", "No-cache");
                response.setHeader("Cache-Control", "no-cache");
                response.setDateHeader("Expires", 0L);
            }

            logger.debug(content);
            response.getWriter().write(content);
            response.getWriter().flush();
        } catch (IOException var11) {
            logger.error(var11.getMessage(), var11);
        }

    }

    public static void renderText(String text, String... headers) {
        render("text/plain", text, headers);
    }

    public static void renderHtml(String html, String... headers) {
        render("text/html", html, headers);
    }

    public static void renderXml(String xml, String... headers) {
        render("text/xml", xml, headers);
    }

    public static void renderJson(String jsonString, String... headers) {
        render("application/json", jsonString, headers);
    }

    public static void renderJson(Object object) {
        renderJson(object, false);
    }

    public static void renderJson(Object object, boolean ignoreNull) {
        String jsonString = JsonUtil.obj2Json(object, ignoreNull);
        render("application/json", jsonString);
    }

    public static void renderJsonp(String callbackName, Map contentMap, String... headers) {
        String jsonParam = JsonUtil.obj2Json(contentMap, false);
        StringBuilder result = (new StringBuilder()).append(callbackName).append("(").append(jsonParam).append(");");
        render("text/javascript", result.toString(), headers);
    }
}
