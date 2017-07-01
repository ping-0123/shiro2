package com.github.zhangkaitao.shiro.chapter19.exception;

public class DataNotFoundException extends Exception {
	private static final long serialVersionUID = 973726680731998227L;
	
	private Class<?> entity;
	
	private String propertyName;
	
	private Object value;

	public DataNotFoundException() {
		super();
	}
	
	public DataNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DataNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataNotFoundException(String message) {
		super(message);
	}

	public DataNotFoundException(Throwable cause) {
		super(cause);
	}





	public DataNotFoundException(Class<?> entity, String propertyName, Object value){
		super("can not found data from  database's table that mapped by entity" +  entity.getSimpleName()
			+	" by " + propertyName + "=" + value.toString());
		this.entity = entity;
		this.propertyName = propertyName;
		this.value = value;
	}

	public Class<?> getEntity() {
		return entity;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public Object getValue() {
		return value;
	}

	public void setEntity(Class<?> entity) {
		this.entity = entity;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	

}
