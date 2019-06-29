package com.simplefanc.user;

import com.simplefanc.server.Request;
import com.simplefanc.server.Response;
import com.simplefanc.server.Servlet;

public class RegisterServlet implements Servlet {
    @Override
    public void service(Request request, Response response) {
        //关注业务逻辑
        String name = request.getParameter("name");
        String[] favs = request.getParameterValues("fav");
//        response.println("你注册的信息为:"+name);
//        response.println("你喜欢的类型为:");
//        for (String v : favs){
//            if(v.equals("0"))
//                response.println("0");
//            else if(v.equals("1"))
//                response.println("1");
//            else if(v.equals("2"))
//                response.println("2");
//        }
//        response.print("<!DOCTYPE html>");
//        response.print("<html lang=\"en\">");
//        response.print("<head>");
//        response.print("<meta charset=\"UTF-8\">");
//        response.print("<title>登录</title>");
//        response.print("</head>");
//        response.print("<body>");
//        response.print("欢迎回来"+request.getParameter("name"));
//        response.print("</body>");
//        response.print("</html>");
        response.print("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<form action=\"http://localhost:8888/reg\" method=\"post\">\n" +
                "    <input type=\"text\" name=\"name\"><br>\n" +
                "    <input type=\"checkbox\" name=\"fav\" value=\"0\"><br>\n" +
                "    <input type=\"checkbox\" name=\"fav\" value=\"1\"><br>\n" +
                "    <input type=\"checkbox\" name=\"fav\" value=\"2\"><br>\n" +
                "    <input type=\"submit\" value=\"submit\">\n" +
                "</form>\n" +
                "</body>\n" +
                "</html>");
    }
}
