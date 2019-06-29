package com.simplefanc.user;

import com.simplefanc.server.Request;
import com.simplefanc.server.Response;
import com.simplefanc.server.Servlet;

//解耦到业务处理类
public class LoginServlet implements Servlet {
    @Override
    public void service(Request request, Response response) {
        //关注响应内容
        response.print("<html>");
        response.print("<head>");
        response.print("<title>第一个小脚本<title>");
        response.print("</head>");
        response.print("<body>");
        response.print("欢迎回来"+request.getParameter("name"));
        response.print("</body>");
        response.print("</html>");
    }
}
