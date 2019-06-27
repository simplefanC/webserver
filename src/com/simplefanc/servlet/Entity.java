package com.simplefanc.servlet;

/**
 *<servlet>
 *    <servlet-name>loginServlet</servlet-name>
 *    <servlet-class>com.simplefanc.servlet.loginServlet</servlet-class>
 *</servlet>
 */

public class Entity {
    private String servletName;
    private String servletClass;

    public Entity() {
    }

    public String getServletName() {
        return servletName;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    public String getServletClass() {
        return servletClass;
    }

    public void setServletClass(String servletClass) {
        this.servletClass = servletClass;
    }
}
