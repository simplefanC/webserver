package com.simplefanc.servlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebContext {
    private List<Entity> entityList = null;
    private List<Mapping> mappingList = null;

    //servlet-name
    //servlet-class
    private Map<String, String> entityMap = new HashMap<>();

    //url-pattern
    //servlet-name
    private Map<String, String> mappingMap = new HashMap<>();

    public WebContext(List<Entity> entityList, List<Mapping> mappingList) {
        this.entityList = entityList;
        this.mappingList = mappingList;
        //loginServlet-->com.simplefanc.servlet.loginServlet
        //registerServlet-->com.simplefanc.servlet.registerServlet
        for (Entity e : entityList){
            entityMap.put(e.getServletName(), e.getServletClass());
        }
        //loginServlet-->[/login]
        //registerServlet-->[/reg, /register]

        for (Mapping m : mappingList){
            for (String urlPattern : m.getUrlPatterns())
                mappingMap.put(urlPattern, m.getServletName());
        }

    }

    //url-pattern --> servlet-name --> servlet-class
    public String getServletClass(String urlPattern){
        return entityMap.get(mappingMap.get(urlPattern));
    }
}
