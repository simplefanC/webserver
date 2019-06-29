package com.simplefanc.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.*;

/**
 * 封装请求协议: 封装请求参数为map
 */
public class Request {
    //协议信息
    private String requestInfo;
    //请求方式
    private String requestMethod;
    //Request URL
    private String requestURL;
    //请求参数
    private String queryStr = "";
    //存储参数
    private Map<String, List<String>> parameterMap;

    public Request(Socket client) throws IOException {
        this(client.getInputStream());
    }

    public Request(InputStream is){
        parameterMap = new HashMap<>();
        byte[] datas = new byte[1024*1024*1024];
        int len = 0;
        try {
            len = is.read(datas);
//            System.out.println(len+":len");
            if(len == -1)
                return;
            this.requestInfo = new String(datas, 0, len);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        //分解字符串
        parseRequestInfo();
    }

    //http://localhost:8888/favicon.ico ???bug
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
            queryStr = urlArray[1];
        }
        System.out.println(requestURL);

        //3. 获取请求参数 如果是GET已经获取,如果是POST可能在请求体中
        String CRLF = "\r\n";
        if(requestMethod.equals("post")){
            String qStr = this.requestInfo.substring(this.requestInfo.lastIndexOf(CRLF)).trim();
            if(queryStr == null)
                queryStr = qStr;
            else if(!qStr.equals(""))
                queryStr += "&" + qStr;
        }

        System.out.println(requestMethod+"-->"+requestURL+"-->"+queryStr);
        //多选情况 fav=1&fav=2
        //转成map fav=1&fav=2&name=cf&age=18&other=
        convertMap();
    }

    //处理请求参数为map
    private void convertMap(){
        //分割字符串 &
        String[] keyValues = this.queryStr.split("&");
        for(String str : keyValues){
            //再次分割字符串 =
            String[] kv = str.split("=");
            kv = Arrays.copyOf(kv, 2);//保证长度为2
            //获取key和values
            String key = kv[0];
            String value = kv[1] == null ? null : decode(kv[1], "UTF-8");//中文需处理
            //存储到map
            if(!parameterMap.containsKey(key)){//第一次
                parameterMap.put(key, new ArrayList<String>());
            }
            parameterMap.get(key).add(value);
        }
    }

    /**
     * 处理中文
     * @return
     */
    private String decode(String value, String enc) {
        String val = "";
        try {
            val = URLDecoder.decode(value, enc);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return val;
    }

    /**
     * 通过name获取多个对应的值
     * @param key
     * @return
     */
    public String[] getParameterValues(String key){
        List<String> vals = this.parameterMap.get(key);
        if(vals == null || vals.size() < 1){
            return null;
        }
        return vals.toArray(new String[0]);
    }

    /**
     * 过name获取对应的一个值
     * @param key
     * @return
     */
    public String getParameter(String key){
        String[] values = getParameterValues(key);
        return values == null ? null : values[0];
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public String getQueryStr() {
        return queryStr;
    }
}
