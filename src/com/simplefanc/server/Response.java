package com.simplefanc.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

/**
 * 封装响应协议
 */
public class Response {
    private BufferedWriter bw;
    //正文
    private StringBuilder content;
    //协议头信息 状态行 请求头 回车
    private StringBuilder headInfo;
    //正文字节数
    private int size;

    private final String BLANK = " ";
    private final String CRLF = "\r\n";

    public Response() {
        content = new StringBuilder();
        headInfo = new StringBuilder();
        size = 0;
    }
    public Response(Socket client) {
        this();
        try {
            bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            headInfo = null;
        }
    }
    public Response(OutputStream os){
        this();
        bw = new BufferedWriter(new OutputStreamWriter(os));
    }

    //动态添加内容
    public Response print(String info){
        content.append(info);
        size += info.getBytes().length;
        return this;
    }
    public Response println(String info){
        content.append(info).append(CRLF);
        size += (info + CRLF).getBytes().length;
        return this;
    }

    //构建头信息
    private void createHeadInfo(int statusCode){
        //1. 响应行 HTTP/1.1 200 OK
        String reasonPhrase = "";
        headInfo.append("HTTP/1.1").append(BLANK);
        headInfo.append(statusCode).append(BLANK);
        switch (statusCode){
            case 200:
                reasonPhrase = "OK";
                break;
            case 404:
                reasonPhrase = "Not Found";
                break;
            case 500:
                reasonPhrase = "Internal Server Error";
                break;
        }
        headInfo.append(reasonPhrase).append(CRLF);
        //2. 响应头（最后一行存在空行）
        headInfo.append("Date:").append(new Date()).append(CRLF);
        headInfo.append("Server:").append("simplefanc Server/1.0").append(CRLF);
        headInfo.append("Content-Type: text/html;charset=utf-8").append(CRLF);
        headInfo.append("Content-Length:").append(size).append(CRLF);
        headInfo.append(CRLF);//空行
    }

    //推送响应头信息
    public void pushToClient(int statusCode) {
        if(headInfo == null){
            statusCode = 500;
        }
        createHeadInfo(statusCode);
        try {
            bw.append(headInfo.toString());
            bw.append(content.toString());
            System.out.println("bw");
            System.out.println(headInfo.toString()+content.toString());
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
