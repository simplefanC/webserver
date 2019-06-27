package com.simplefanc.servlet;

import java.util.HashSet;
import java.util.Set;

/**
 * <servlet-mapping>
 *     <servlet-name>registerServlet</servlet-name>
 *     <url-pattern>/register</url-pattern>
 *     <url-pattern>/reg</url-pattern>
 * </servlet-mapping>
 *
 */

public class Mapping {
    private String servletName;
    private Set<String> urlPatterns;

    public Mapping() {
        urlPatterns = new HashSet<String>();//初始化
    }

    public String getServletName() {
        return servletName;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    public Set<String> getUrlPatterns() {
        return urlPatterns;
    }

    public void setUrlPatterns(Set<String> urlPatterns) {
        this.urlPatterns = urlPatterns;
    }

    public void addUrlPattern(String pattern){
        this.urlPatterns.add(pattern);
    }
}
