package com.simplefanc.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 分发器
 * 加入状态内容处理 404 500 首页
 */
public class Dispatcher implements Runnable {

    private Socket client;
    private Request request;
    private Response response;

    public Dispatcher(Socket client) {
        this.client = client;
        try {
            //获取请求协议
            request = new Request(client);
            //获取响应协议
            response = new Response(client);
        } catch (IOException e) {
            e.printStackTrace();
            this.release();
        }
    }

    @Override
    public void run() {
        try {
            byte[] datas = new byte[1024*1024];
            int len = 0;
            if(request.getRequestURL() == null || request.getRequestURL().equals("")){
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("index.html");
                len = is.read(datas);
                response.print(new String(datas, 0, len));
//                System.out.println(new String(datas, 0, len));
                is.close();
                return;
            }
            Servlet servlet = WebApp.getServletFromUrl(request.getRequestURL());
            if(servlet != null){
                servlet.service(request, response);
                //关注状态码
                response.pushToClient(200);
//                response.println("");
            }else {
                //多线程只能通过类加载器加载文件
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("error.html");
                len = is.read(datas);
                response.print(new String(datas, 0, len));
//                System.out.println(new String(datas, 0, len));
                response.pushToClient(404);
                is.close();
            }
            release();//短连接
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 释放资源 后期封装Util类
     */
    private void release(){
        try {
            client.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
