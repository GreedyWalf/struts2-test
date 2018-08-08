package com.qs.common.base;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.qs.common.utils.Struts2Utils;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.ParameterizedType;

public abstract class BaseAction<M> extends ActionSupport implements ModelDriven<M>, Preparable {
    private Boolean isAjax = false;
    private static final long serialVersionUID = 1L;
    protected M model;
    //定义返回的页面，struts配置文件中用来设置动态文件路由
    private String target;
    public static final String COMMON = "common";
    protected HttpServletRequest request = ServletActionContext.getRequest();
    protected HttpServletResponse response = ServletActionContext.getResponse();

    public BaseAction(M model) {
        this.model = model;
    }

    public BaseAction() {
        try {
            Class<M> m = (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            this.model = m.newInstance();
        } catch (InstantiationException var2) {
            var2.printStackTrace();
        } catch (IllegalAccessException var3) {
            var3.printStackTrace();
        }
    }

    public String execute() throws Exception {
        return "common";
    }

    public String render(String target) {
        this.setTarget(target);
        return "common";
    }

    public String renderJson(Object object) {
        Struts2Utils.renderJson(object);
        return null;
    }

    public String renderJsonString(String jsonString) {
        Struts2Utils.render("application/json", jsonString, new String[0]);
        return null;
    }

    public String getTarget() {
        return this.target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getContextPath() {
        return ServletActionContext.getRequest().getContextPath();
    }

    //模型驱动，需要定义的get方法
    public M getModel() {
        return this.model;
    }

    public void prepare() throws Exception {
    }

    public Boolean getIsAjax() {
        return this.isAjax();
    }

    private boolean isAjax() {
        String header = this.request.getHeader("X-Requested-With");
        return header != null && "XMLHttpRequest".equalsIgnoreCase(header);
    }

    public void setIsAjax(Boolean isAjax) {
        this.isAjax = isAjax;
    }
}
