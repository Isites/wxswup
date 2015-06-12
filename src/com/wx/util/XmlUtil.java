package com.wx.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlUtil {
	
	//将接受到的xml信息传递到这个类里面去
	public static Object xml2obj(Document entry, Class<?> c) throws Exception{
		
		Object obj = c.newInstance();
		Element root = (Element) entry.getRootElement();
		@SuppressWarnings("unchecked")
		Iterator<Element> iterator = root.elementIterator();
		
		while(iterator.hasNext()){
			//System.out.println(iterator.next().getName());
			Element e = iterator.next();
			Method method = c.getMethod("set"+e.getName(),String.class);
			
			method.invoke(obj, e.getText());
		}
		return obj;
	}
	
	
	//xmstream 框架在sae上面不被支持（xmstream反射了sun.misc.Unsafe类，被sae封杀了）
	//所以采用另外一种方法实现objtoxml的转换
	private static void iterElement(Element root,HashMap<String,String> map
			,Object obj){
		Iterator<String> iter  = map.keySet().iterator();
		
		while(iter.hasNext()){
			String key = iter.next();
			Element element = root.addElement(key);
			element.setText(map.get(key));
			try {
				Method method = obj.getClass().getMethod("get"+key);
				if(method.getReturnType().getName().equals(HashMap.class.getName()))
					iterElement(element, map, obj);
					
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	//将一个对象转换为适合微信接口的xml字符串，不能处理潜逃里面继续嵌套
	public static String obj2xml(Object obj){
		
		//判断是否需要嵌套xml结构,默认没有
				boolean isneed = false;
				//是否已经存在同名标签
				boolean isexist = false;
				//保存xml中的嵌套结构
				ArrayList<HashMap> list = new ArrayList<HashMap>();
				Method method[] = obj.getClass().getMethods();
				Document document = DocumentHelper.createDocument();
				Element root = document.addElement("xml");
				//先将嵌套xml结构添加进文档中
				for(Method m : method){
					String name = m.getName();
					if(m.getName().startsWith("get")&& !name.equals("getClass")){
						Object content = null;
						name = name.substring(3, name.length());
						try {
							content = m.invoke(obj);
						} catch (Exception e) {
							e.printStackTrace();
						} 
						if(content instanceof HashMap){
							Element element = root.addElement(name);
							HashMap<String, String> map = (HashMap<String, String>) content;
							list.add(map);
							isneed = true;
							iterElement(element, map, obj);
						}
					}	
				}
				//将非嵌套结构添加进去，并且保证非嵌套结构中出现嵌套结构中的同名element
				for(Method m : method){
					String name = m.getName();
					if(m.getName().startsWith("get")&& !name.equals("getClass")){
						Object content = null;
						name = name.substring(3, name.length());
						try {
							content = m.invoke(obj);
						} catch (Exception e) {
							e.printStackTrace();
						}
						if(isneed){//需要作嵌套的重复处理
						EXIT:for(HashMap<String, String> map : list){
								Iterator<String> iterator = map.keySet().iterator();
								while(iterator.hasNext()){
									String name1 = iterator.next();
									if(name.equals(name1)){
										isexist = true;
										//跳出所有循环
										break EXIT;
									}
								}
							}
						    if(!isexist && !(content instanceof HashMap)){
						    	Element element = root.addElement(name);
								element.setText((String) content+"");
						    }
						    isexist = false;
						}
						else{//不需要作嵌套的重复处理
							Element element = root.addElement(name);
							element.setText((String) content+"");
						}
					}
				}
		return document.asXML();
	}

}
