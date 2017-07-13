package com.github.zhangkaitao.shiro.common;

import java.lang.reflect.Field;

public class ReflectUtil {
	
	/**
	 * Returns an array of Field objects reflecting all the fields declared by the class or interface represented by this Class object. 
	 * This includes public, protected, default (package) access, and private fields, also includes inherited fields. 
	 * @param clazz
	 * @return
	 * 
	 */
	public static Field[] getAllFields(Class<?> clazz){
		if(clazz == null) throw new IllegalArgumentException("clazz 不能为null");
		
		if(clazz != Object.class){
			Field[] fields = clazz.getDeclaredFields();
			Class<?> superClazz = clazz.getSuperclass();
			Field[] superFields = ReflectUtil.getAllFields(superClazz);
			if(fields==null){
				return superFields;
			}else {
				if(superFields == null)
					return fields;
				else {
					return ArrayUtil.concat(fields, superFields);
				}
			}
		}
		return null;
	}
}
