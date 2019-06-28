package com.simplefanc;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 封装请求协议
 * 获取请求协议 URI 以及请求参数
 */
public class Request {
    //协议信息
    private String requestInfo;
    //请求方式
    private String requestMethod;
    //Request URL
    private String requestURL;
    //请求参数
    private String queryParameter;

    public Request(Socket client) throws IOException {
        this(client.getInputStream());
    }

    public Request(InputStream is){
        byte[] datas = new byte[1024*1024];
        int len = 0;
        try {
            len = is.read(datas);
            this.requestInfo = new String(datas, 0, len);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        //分解字符串
        parseRequestInfo();
    }

    private void parseRequestInfo(){
        System.out.println("分解");
        System.out.println(requestInfo);

        //1. 获取请求方式 开头到以一个/前" "
        this.requestMethod = this.requestInfo.substring(0, this.requestInfo.indexOf("/") - 1).toLowerCase();
        System.out.println(requestMethod);//"post"

        //2. 获取请求url 第一个"/"到"HTTP/"
        //可能包含请求参数 ?前面的为url
        int startIndex = this.requestInfo.indexOf("/") + 1;// "/"后面字符位置
        int endIndex = this.requestInfo.indexOf("HTTP/") - 1;//H位置前一个" "

        this.requestURL = this.requestInfo.substring(startIndex, endIndex);//"aaa?name=cf"

        //获取?位置
        int queryIndex = this.requestURL.indexOf("?");
        if(queryIndex >= 0){//存在请求参数
            String[] urlArray = this.requestURL.split("\\?");
            this.requestURL = urlArray[0];//"aaa"
            queryParameter = urlArray[1];
        }
        System.out.println(requestURL);

        //3. 获取请求参数 如果是GET已经获取,如果是POST可能在请求体中
        String CRLF = "\r\n";
        if(requestMethod.equals("post")){
            String qStr = this.requestInfo.substring(this.requestInfo.lastIndexOf(CRLF)).trim();
            if(queryParameter == null)
                queryParameter = qStr;
            else if(!qStr.equals(""))
                queryParameter += "&" + qStr;
        }

        System.out.println(requestMethod+"-->"+requestURL+"-->"+queryParameter);

    }

}
