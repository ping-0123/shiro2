package com.github.zhangkaitao.shiro.chapter19.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.zhangkaitao.shiro.chapter19.dao.BaseDao;
import com.github.zhangkaitao.shiro.chapter19.entity.PageBean;
import com.github.zhangkaitao.shiro.chapter19.exception.DataNotFoundException;
import com.github.zhangkaitao.shiro.chapter19.service.BaseService;


public abstract class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {
	protected  Log logger = LogFactory.getLog(getClass());

	private BaseDao<T, PK> baseDao;
	public final BaseDao<T, PK> getBaseDao() {
		return baseDao;
	}
	public final void setBaseDao(BaseDao<T, PK> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public T get(PK id) {
		return baseDao.get(id);
	}
	@Override
	public PK save(T entity) {
		return baseDao.save(entity);
	}
	@Override
	public void saveOrUpdate(T entity) {
		baseDao.saveOrUpdate(entity);
	}
	
	@Override
	public void modify(T source, T target) throws IllegalArgumentException, IllegalAccessException{
		baseDao.modify(source, target);
	}
	
	@Override
	public void modify(PK id, T entity) throws DataNotFoundException, IllegalArgumentException, IllegalAccessException{
		baseDao.modify(id, entity);
	}
	
	@Override
//	@Cacheable(value="service", keyGenerator="keyGenerator")
	public List<T> findAll(){
		return baseDao.findAll();
	}
	
	@Override
	public int findCount(){
		return baseDao.findCount();
	}
	
	@Override
	public List<T> findByProperty(String propertyName, Object value){
		return baseDao.findByProperty(propertyName, value);
	}
	@Override
	public int findCountByProperty(String propertyName, Object value) {
		return baseDao.findCountByProperty(propertyName, value);
	}
	@Override
	public List<T> findByProperties(String[] propertyNames, Object[] values){
		return baseDao.findByProperties(propertyNames, values);
	}

	@Override
	public List<T> findByExample(T entity){
		return baseDao.findByExample(entity);
	}

	@Override
	public void delete(T entity) {
		baseDao.delete(entity);
	}

	@Override
	public void delete(PK id) {
		baseDao.delete(id);
	}

	@Override
	public void update(T entity) {
		baseDao.update(entity);
	}


	@Override
	public int findCountByProperties(String[] propertyNames, Object[] values){
		return baseDao.findCountByProperties(propertyNames, values);
	}
	
	
	@Override
	public PageBean<T> findPageOfAll(int pageNo, int pageSize){
		return baseDao.findPageOfAll(pageNo, pageSize);
	}
	
	@Override
	public PageBean<T> findPageByProperties(String[] propertyNames, Object[] values, int pageNo, int pageSize){
		return baseDao.findPageByProperties(propertyNames, values, pageNo, pageSize);
	}
	
	@Override
	public PageBean<T> findPageByProperty(String propertyName, Object value, int pageNo, int pageSize){
		return baseDao.findPageByProperty(propertyName, value, pageNo, pageSize);
	}
}
