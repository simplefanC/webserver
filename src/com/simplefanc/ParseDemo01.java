package com.simplefanc;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class ParseDemo01 {

	/**
	 * @param args
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		//1，获取解析工厂
		SAXParserFactory factory = SAXParserFactory.newInstance();
		//2. 从解析工厂获取解析器
		SAXParser parse = factory.newSAXParser();
		//3. 编写处理器
		//4. 加载文档Document注册处理器
		PersonHandler handler = new PersonHandler();
		//当前线程的类加载器获取
		parse.parse(Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("com/simplefanc/person.xml")
				,handler );
		
		List<Person> persons = handler.getPersons();
		for(Person p : persons){
			System.out.println(p.getName()+"-->"+p.getAge());
		}

	}

}
