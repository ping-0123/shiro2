package com.github.zhangkaitao.shiro.chapter19.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.github.zhangkaitao.shiro.chapter19.dao.BaseDao;
import com.github.zhangkaitao.shiro.chapter19.entity.BaseEntity;
import com.github.zhangkaitao.shiro.chapter19.entity.PageBean;





public abstract class BaseDaoImpl<T,PK extends Serializable> 
				extends HibernateDaoSupport 
				implements BaseDao<T, PK> {

	private Class<T> entityClass;  
    protected SessionFactory sessionFactory;  
      
    @SuppressWarnings("unchecked")
	public BaseDaoImpl() {  
        this.entityClass = null;  
        Class<?> c = getClass();  
        Type type = c.getGenericSuperclass();  
        if (type instanceof ParameterizedType) {  
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();  
            this.entityClass = (Class<T>) parameterizedType[0];  
        }  
    }  
      
    @Resource  
    public void setHibernateSessionFactory(SessionFactory sessionFactory) {  
        this.sessionFactory = sessionFactory; 
        super.setSessionFactory(sessionFactory);
    }  
  
    protected Session getSession() {  
    	try {
    		 return getHibernateTemplate().getSessionFactory().getCurrentSession();  
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
       
    }  
  
    public T get(PK id){  
        Assert.notNull(id, "id is required"); 
    	return (T) getHibernateTemplate().get(entityClass, id);  
    } 
  
	@SuppressWarnings("unchecked")
	public PK save(T entity)  {  
        Assert.notNull(entity, "entity is required");  
        try {
        	if(entity instanceof BaseEntity)
        		((BaseEntity) entity).init();
        	return (PK) getHibernateTemplate().save(entity);  
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByProperty(String propertyName, Object value){
		String hql = "from " + entityClass.getSimpleName() + " where " + propertyName + " =:value";
		List<T> list =  (List<T>) getHibernateTemplate().findByNamedParam(hql, "value", value);
		if(list==null) return new ArrayList<>();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int findCountByProperty(String propertyName, Object value) {
		String hql = "select count(*) from " + entityClass.getSimpleName() + " where " + propertyName + " =:value";
		try {
			List<Long> l = (List<Long>) getHibernateTemplate().findByNamedParam(hql, "value", value);
			return l.get(0).intValue();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return 0;
		}
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(){
		String hql = "FROM "+  entityClass.getSimpleName();
		List<T> list =  (List<T>) getHibernateTemplate().find(hql);
		if(list==null) return new ArrayList<>();
		return list;
	}

	@Override
	public void saveOrUpdate(T entity) {
		Assert.notNull(entity, "entity is required");
		try {
			getHibernateTemplate().saveOrUpdate(entity);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public void delete(T entity) {
		Assert.notNull(entity, "entity is required");
		try{
			getHibernateTemplate().delete(entity);
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		
	}

	@Override
	public void delete(PK id) {
		Assert.notNull(id, "id is required");
		try{
			T entity = get(id);
			if (entity !=null){
				delete(entity);
			}
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
	}



	@Override
	public List<T> findByExample(T entity){
		Assert.notNull(entity, "entity is required");
		List<T> list =   getHibernateTemplate().findByExample(entity);
		if(list==null) return new ArrayList<>();
		return list;
	}  
	
	@Override
	public void update(T entity){
		Assert.notNull(entity, "entity is required");
		try{
			if(entity instanceof BaseEntity){
				BaseEntity baseEntity = (BaseEntity) entity;
				baseEntity.setLastModifiedDate(new Date());
				getHibernateTemplate().update(baseEntity);
				return;
			}
			getHibernateTemplate().update(entity);
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public int findCount() {
		String hql = "select count(*) from " + entityClass.getSimpleName();
		try{
			List<Long> sums =   (List<Long>) getHibernateTemplate().find(hql);
			return sums.get(0).intValue();
		}catch (Exception e) {
			logger.error(e.getMessage());
			return 0;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByProperties(String[] propertyNames, Object[] values){
		if(propertyNames.length != values.length){
			throw new IllegalArgumentException("传入的属性名和属性值数量不一致");
		}
		String[] properties = new String[propertyNames.length];
		StringBuilder builder = new StringBuilder();
		builder.append("FROM " + entityClass.getSimpleName());
		builder.append(" WHERE 1=1");
		for(int i = 0; i<propertyNames.length; i++){
			if(StringUtils.hasLength(propertyNames[i])){
				properties[i] = propertyNames[i].replace(".", "");
				builder.append(" AND " + propertyNames[i] + "=:" +properties[i]);
			}else {
				throw new IllegalArgumentException("属性名不能为空为null");
			}
		}
		List<T> list =   (List<T>) getHibernateTemplate().findByNamedParam(
				builder.toString(), properties, values);
		if(list == null) return new ArrayList<>();
		return list;
	}

	@Override
	public int findCountByProperties(String[] propertyNames, Object[] values){
		if(propertyNames.length != values.length){
			throw new IllegalArgumentException("传入的属性名和属性值数量不一致");
		}
		String[] properties = new String[propertyNames.length];
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT COUNT(*)");
		builder.append(" FROM " + entityClass.getSimpleName());
		builder.append(" WHERE 1=1");
		for(int i = 0; i<propertyNames.length; i++){
			if(StringUtils.hasLength(propertyNames[i])){
				properties[i] = propertyNames[i].replace(".", "");
				builder.append(" AND " + propertyNames[i] + "=:" +properties[i]);
			}else {
				throw new IllegalArgumentException("属性名不能为空为null");
			}
		}
		@SuppressWarnings("unchecked")
		List<Long> count =   (List<Long>) getHibernateTemplate().findByNamedParam(
				builder.toString(), properties, values);
		return count.get(0).intValue();
	}

	

	
	@Override
	public PageBean<T> findPageByHql(String hql, int pageNum, int pageSize){
		Assert.hasLength(hql);
		if(pageNum <=0)
			pageNum =1;
		if(pageSize<=0)
			pageSize = PageBean.DEFAULT_PAGE_SIZE;
		
		int totalRecords =  findCountByHql(_get_count_hql(hql));
		if(totalRecords ==0)
			return new PageBean<>(pageSize, pageNum, totalRecords, new ArrayList<>());
		
		Query<?> query = getSession().createQuery(hql);
		query.setFirstResult((pageNum-1) * pageSize);
		query.setMaxResults(pageSize);
		@SuppressWarnings({ "unchecked", "deprecation" })
		List<T> list =(List<T>) query.list();
		
		return new PageBean<>(pageSize, pageNum, totalRecords,list);
	}
	
		
	@SuppressWarnings("unchecked")
	public int findCountByHql(String hql){
		try{
			List<Long> list=   (List<Long>) getHibernateTemplate().find(hql);
			return list.get(0).intValue();
		}catch (Exception e) {
			logger.error(e.getMessage());
			return 0;
		}
	}
	
	private String _get_count_hql(String hql){
		int i = hql.toUpperCase().indexOf("FROM");
		return "select count(*) " + hql.substring(i);
	}
	
	@SuppressWarnings("unused")
	private Throwable _get_root_Exception(Throwable e)
	{
		Throwable next = e.getCause();
		if(next == null)
			return e;
		else
			return _get_root_Exception(next);
	}
	
}

