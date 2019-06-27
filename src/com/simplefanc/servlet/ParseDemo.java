package com.simplefanc.servlet;

import com.simplefanc.Person;
import com.simplefanc.PersonHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ParseDemo {

	/**
	 * @param args
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		//1，获取解析工厂
		SAXParserFactory factory = SAXParserFactory.newInstance();
		//2. 从解析工厂获取解析器
		SAXParser parse = factory.newSAXParser();
		//3. 编写处理器
		//4. 加载文档Document注册处理器
		WebHandler handler = new WebHandler();
		//当前线程的类加载器获取
		parse.parse(Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("com/simplefanc/servlet/web.xml")
				,handler );

		List<Entity> entityList = handler.getEntityList();
//		for(Entity e : entityList){
//			System.out.println(e.getServletName()+"-->"+e.getServletClass());
//		}

		List<Mapping> mappingList = handler.getMappingList();
//		for(Mapping m : mappingList){
//			System.out.println(m.getServletName()+"-->"+m.getUrlPatterns());
//		}
		// 获取数据
		WebContext context = new WebContext(handler.getEntityList(), handler.getMappingList());
		//假设请求/login
		String className = context.getServletClass("/reg");
		Class clazz = Class.forName(className);
		Servlet servlet = (Servlet) clazz.getConstructor().newInstance();
		servlet.service();

	}

}
