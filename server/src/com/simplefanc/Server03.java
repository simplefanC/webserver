package com.simplefanc;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * 返回响应协议
 */
public class Server03 {
    private ServerSocket serverSocket;
    public static void main(String[] args) {
        Server03 server = new Server03();
        server.start();
    }
    //启动服务
    public void start(){
        try {
            serverSocket = new ServerSocket(8888);
            receive();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器启动失败...");
        }
    }
    //接受连接处理
    public void receive(){
        try {
            Socket client = serverSocket.accept();
            System.out.println("一个客户端建立了连接...");
            Request request = new Request(client);

            /*

            HTTP/1.1 200 OK
            Date: Sat, 01 Jul 2017 14:51:26 GMT
            Server: Apache/2.4.7 (Ubuntu)
            Set-Cookie: JSESSIONID=84C993F5E433C4DE9BFBA57150FFC065.ajp13_worker;path=/;HttpOnly
            Content-Language: zh-CN
            Vary: Accept-Encoding
            Content-Encoding: gzip
            Content-Length: 7333
            Keep-Alive: timeout=5, max=100
            Connection: Keep-Alive
            Content-Type: text/html;charset=UTF-8

            <html>
            <head>
            <title>title of html.</html>
            </head>
            <body>
            <h1>Hello world!</h1>
            </body>
            </html>

            */
            String content =
                    "<html>" +
                    "<head>" +
                    "<title>title of html.</html>" +
                    "</head>" +
                    "<body>" +
                    "<h1>Hello world!</h1>" +
                    "</body>" +
                    "</html>";
            //Content-Length: 7333 为字节数
            int size = content.getBytes().length;
            String blank = " ";
            String CRLF = "\r\n";
            StringBuilder responseInfo = new StringBuilder();
            //返回
            //1. 状态行 HTTP/1.1 200 OK
            responseInfo.append("HTTP/1.1").append(blank).append(200).append(blank).append("OK").append(CRLF);
            //2. 响应头（最后一行存在空行）
            responseInfo.append("Date:").append(new Date()).append(CRLF);
            responseInfo.append("Server:").append("simplefanc Server/1.0").append(CRLF);
            responseInfo.append("Content-Type: text/html;charset=UTF-8").append(CRLF);
            responseInfo.append("Content-Length:").append(size).append(CRLF);
            responseInfo.append(blank);
            //3. 主体
            responseInfo.append(content);

            //写出到客户端
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            bw.write(responseInfo.toString());
            bw.flush();//避免驻留在内存


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("客户端错误");
        }
    }
    //停止服务
    public void stop(){

    }
}
