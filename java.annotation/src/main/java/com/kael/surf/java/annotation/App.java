package com.kael.surf.java.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.jws.Oneway;

import com.kael.surf.java.annotation.sql.Column;
import com.kael.surf.java.annotation.sql.Table;
import com.kael.surf.java.annotation.sql.User;

/**
 * Hello world!
 * 
 */
public class App {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		//test1();
		User user0 = new User();
		user0.setId(10000);
		System.out.println(query(user0));
		
		User user1 = new User();
		user1.setAge(80);
		user1.setUserName("dsd");
		System.out.println(query(user1));
		
	}

	private static void test1() {
		// System.out.println( "Hello World!" );
		Child person = new Child();
		// person.sing();
		Class<?> cls = person.getClass();

		try {
			Method m = cls.getDeclaredMethod("eyeColor", null);

			if (!m.isAccessible()) {
				m.setAccessible(true);
			}

			Desc desc = m.getAnnotation(Desc.class);
			if (desc != null) {
				System.out.println(desc.desc());
				System.out.println(desc.age());
				System.out.println(desc.author());
				System.out.println(m.invoke(person, null));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static String query(Object u){
		if(u == null){
			return null;
		}
		Class<?> cls = u.getClass();
		if(!cls.isAnnotationPresent(Table.class)){
			return null;
		}
		
		String tb_name = cls.getAnnotation(Table.class).value();
		StringBuilder builder = new StringBuilder();
		builder.append("select * from ").append(tb_name).append(" where 1 = 1");
		
		Field[] fs = cls.getDeclaredFields();
		for(Field f : fs){
			if(!f.isAnnotationPresent(Column.class)){
				continue;
			}
			
			Column column = f.getAnnotation(Column.class);
			String fName = f.getName();
			String mName = "get"+fName.substring(0, 1).toUpperCase()+fName.substring(1);
			Object value = null;
			try{
				value = cls.getMethod(mName).invoke(u);
			}catch (Exception e) {
				e.printStackTrace();
			}
			if(value != null){
				builder.append(" and ").append(column.value()).append(" = '").append(value).append("'");
			}
		}
		return builder.toString();
	}
}
