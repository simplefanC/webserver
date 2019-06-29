package com.simplefanc.server;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理器
 */
public class WebHandler extends DefaultHandler {
	private List<Entity> entityList = new ArrayList<Entity>();//初始化
	private List<Mapping> mappingList = new ArrayList<Mapping>();
	private Entity entity;
	private Mapping mapping;
	private String tag;//操作的标签
	private boolean isMapping = false;//操作mapping

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(null != qName){
			tag = qName;

			if(tag.equals("servlet")){
				isMapping = false;
				entity = new Entity();//初始化
			}else if(tag.equals("servlet-mapping")){
				isMapping = true;
				mapping = new Mapping();
			}
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String str = new String(ch, start, length).trim();

		if(tag != null){
			if(isMapping){
				if(tag.equals("servlet-name"))
					mapping.setServletName(str);
				else if(tag.equals("url-pattern"))
					mapping.addUrlPattern(str);
			}else {
				if(tag.equals("servlet-name"))
					entity.setServletName(str);
				else if(tag.equals("servlet-class"))
					entity.setServletClass(str);
			}
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equals("servlet")){
			entityList.add(entity);
		}else if(qName.equals("servlet-mapping"))
			mappingList.add(mapping);
		tag = null;
	}

	public List<Entity> getEntityList() {
		return entityList;
	}

	public List<Mapping> getMappingList() {
		return mappingList;
	}
}
