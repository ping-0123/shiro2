package com.github.zhangkaitao.shiro.chapter19.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.github.zhangkaitao.shiro.chapter19.entity.PageBean;
import com.github.zhangkaitao.shiro.chapter19.exception.DataNotFoundException;


public interface BaseService<T, PK extends Serializable> {
	
	public PK save(T entity);
	public void delete(T entit);
	public void delete(PK id);
	public void update(T entity);
	public void saveOrUpdate(T entity);
	/**
	 * 通过dao.get(id)从数据库中取出一个新实体newEntity, 将newEntity中的属性值
	 * 设置为entity对应的值(entity中某一属性为null, 则newEntity中相应属性值不变)
	 * 改方法用到了反射
	 * 该方法忽略@OneToMany注解的属性的修改
	 * 该方法不能修改从父类继承过来的成员变量的值
	 * 
	 * @param id 需要更改的数据的id
	 * @param entity 需要更改的属性封装在entity中
	 * @throws DataNotFoundException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * 
	 */
	public void modify(PK id, T entity) throws DataNotFoundException, IllegalArgumentException, IllegalAccessException;
	public T get(PK id);
	public List<T> findAll() ;
	public List<T> findByProperty(String propertyName, Object value) ;
	public int findCountByProperty(String propertyName, Object value);
	public int findCountByProperties(String[] propertyNames, Object[] values) ;
	public List<T> findByProperties(String[] propertyNames, Object[] values);
	public List<T> findByExample(T entity);


	public PageBean<T> findPage(T entity, int pageNum, int pageSize);
}
