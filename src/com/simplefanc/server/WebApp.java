package com.simplefanc.server;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class WebApp {
	private static WebContext webContext;
	static {
		try {
			//1，获取解析工厂
			SAXParserFactory factory = SAXParserFactory.newInstance();
			//2. 从解析工厂获取解析器
			SAXParser parse = factory.newSAXParser();
			//3. 编写处理器
			//4. 加载文档Document注册处理器
			WebHandler handler = new WebHandler();
			//当前线程的类加载器获取
			parse.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("web.xml"), handler );
			webContext = new WebContext(handler.getEntityList(), handler.getMappingList());
		}catch (Exception e){
			System.out.println("解析配置文件错误");
		}
	}

	/**
	 * 通过url获取配置文件对应的Servlet
	 * @param url
	 * @return
	 */
	public static Servlet getServletFromUrl(String url){
		//假设请求/login
		String className = webContext.getServletClass("/" + url);
		Class clazz = null;
		Servlet servlet = null;
		try {
			if(className != null){
				clazz = Class.forName(className);
				servlet = (Servlet) clazz.getConstructor().newInstance();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return servlet;
	}

}
