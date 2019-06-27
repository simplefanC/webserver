package com.simplefanc;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PersonHandler extends DefaultHandler {
	private List<Person> persons;
	private Person person;
	private String tag;//操作的标签

	@Override
	public void startDocument() throws SAXException {
		persons = new ArrayList<Person>();//初始化
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		System.out.println(qName + "解析开始");
		if(null != qName){
			tag = qName;
		}
		if(null != qName && qName.equals("person")){
			person = new Person();//初始化
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String str = new String(ch, start, length);
		if(null != tag && tag.equals("name")){
			person.setName(str);
		}else if(null != tag && tag.equals("age")){
			person.setAge(Integer.valueOf(str));
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equals("person")){
			this.persons.add(person);//加入List<Person>
		}
		tag = null;
	}
	
	@Override
	public void endDocument() throws SAXException {
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

}
