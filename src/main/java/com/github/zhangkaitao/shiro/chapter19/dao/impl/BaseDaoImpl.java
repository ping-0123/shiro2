package com.github.zhangkaitao.shiro.chapter19.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.github.zhangkaitao.shiro.chapter19.dao.BaseDao;
import com.github.zhangkaitao.shiro.chapter19.entity.BaseEntity;
import com.github.zhangkaitao.shiro.chapter19.entity.PageBean;
import com.github.zhangkaitao.shiro.chapter19.exception.DataNotFoundException;
import com.github.zhangkaitao.shiro.common.ReflectUtil;





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
			// 更新或者保存前初始化baseEntity属性值
			if(entity instanceof BaseEntity){
				if(((BaseEntity) entity).getId() == null){
					((BaseEntity) entity).init();
				}else {
					((BaseEntity) entity).beforeUpdate();
				}
			}
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
				((BaseEntity) entity).beforeUpdate();
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
		
		 Query<T> query = getSession().createQuery(builder.toString(), entityClass);
		 for(int j=0; j<properties.length;j++){
			 query.setParameter(properties[j], values[j]);
		 }
		 
		 List<T> list = query.getResultList();
//		List<T> list =   (List<T>) getHibernateTemplate().findByNamedParam(
//				builder.toString(), properties, values);
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
		
		if(pageNum <=0) pageNum =PageBean.DEFAULT_PAGE_NO;
		if(pageSize<=0) pageSize = PageBean.DEFAULT_PAGE_SIZE;
		
		int totalRecords =  findCountByHql(_get_count_hql(hql));
		if(totalRecords ==0)
			return new PageBean<>(pageSize, pageNum, totalRecords, new ArrayList<>());
		
		Query<T> query = getSession().createQuery(hql, entityClass);
		query.setFirstResult((pageNum-1) * pageSize);
		query.setMaxResults(pageSize);
		List<T> list = query.getResultList();
		
		return new PageBean<>(pageSize, pageNum, totalRecords,list);
	}
	
	@Override
	public <R> PageBean<R> findPage(String hql, Class<R> resultClass,  String[] namedParameters, Object[] values, int pageNum, int pageSize ){
		Assert.hasLength(hql);
		if(namedParameters.length != values.length){
			throw new IllegalArgumentException("传入的属性名和属性值数量不一致");}
		if(Arrays.asList(namedParameters).contains(null)) throw new IllegalArgumentException("hql的命名参数不能为null");
		int totalRecords = findCount(_get_count_hql(hql), namedParameters, values);
		if(totalRecords == 0)
			return new PageBean<>(pageSize, pageNum, totalRecords, new ArrayList<>());
		
		if(pageNum <=0) pageNum =PageBean.DEFAULT_PAGE_NO;
		if(pageSize<=0) pageSize = PageBean.DEFAULT_PAGE_SIZE;
		int offset = (pageNum-1) * pageSize + 1;
		Query<R> query = getSession().createQuery(hql, resultClass)
				.setFirstResult(offset)
				.setMaxResults(pageSize);
		for(int i=0; i<namedParameters.length; i++){
			query.setParameter(namedParameters[i], values[i]);
		}
		List<R> list = query.getResultList();
		
		return new PageBean<>(pageSize, pageNum, totalRecords,list);
	}
	
	@Override
	public <R> PageBean<R> findPage(String hql, Class<R> resultClass, String namedParameter, Object value, int pageNum, int PageSize){
		if(namedParameter == null || "".equals(namedParameter.trim()))
			throw new IllegalArgumentException("hql的命名参数不能为null");
		String[] namedParameters = {namedParameter};
		Object[] values = {value};
		return findPage(hql, resultClass, namedParameters, values, pageNum, PageSize);
	}
		
	private int findCount(String hql, String[] namedParameters, Object[] values) {
		Query<Long> query = getSession().createQuery(hql, Long.class);
		for(int i=0; i<namedParameters.length; i++){
			query.setParameter(namedParameters[i], values[i]);
		}
		return query.getSingleResult().intValue();
	}

	public int findCountByHql(String hql){
		try{
			return getSession().createQuery(hql, Long.class)
					.getSingleResult()
					.intValue();
		}catch (Exception e) {
			logger.error(e.getMessage());
			return 0;
		}
	}
	
	private String _get_count_hql(String hql){
		int i = hql.toUpperCase().indexOf("FROM");
		return "SELECT COUNT(1) " + hql.substring(i);
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
	
	
	@Override
	public void modify(T source, T target) throws IllegalArgumentException, IllegalAccessException{
		Assert.notNull(source);
		Assert.notNull(target);
		
		Field[] fields = ReflectUtil.getAllFields(source.getClass());
		for (Field f : fields) {
			f.setAccessible(true);
			if(	//静态属性不变
				!Modifier.isStatic(f.getModifiers()) 
				// target属性为null ,source 对应的属性不变
				&&f.get(target)!=null
				//Id 主键不改变
				&& f.getDeclaredAnnotation(Id.class) == null
				//排除OneToMany 映射
				&& f.getDeclaredAnnotation(OneToMany.class) == null
				//属性值相同不就需要修改了
				&& !(f.get(target).equals(f.get(source))))
				
				f.set(source, f.get(target));
		}
		
		update(source);
	}
	
	@Override
	public void modify(PK id, T target) throws DataNotFoundException, IllegalArgumentException, IllegalAccessException{
		Assert.notNull(id);
		Assert.notNull(target);
		
		T source = get(id);
		modify(source, target);
	}
	
	@Override
	public <R> PageBean<R> findPageByCriteria(CriteriaQuery<R> criteria, int pageNo, int pageSize, int totalSize){
		if(pageNo<=0) pageNo = 1;
		if(pageSize<=0) pageSize = PageBean.DEFAULT_PAGE_SIZE;
		Query<R> query = getSession().createQuery(criteria);
		query.setFirstResult(((pageNo-1) * pageSize));
		query.setMaxResults(pageSize);
		List<R> list = query.getResultList();
		
		return new PageBean<>(pageSize, pageNo,totalSize,list);
	}

	@Override
	public PageBean<T> findPageOfAll(int pageNo, int pageSize){
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(entityClass);
		Root<T> root = criteria.from(entityClass);
		criteria.select(root);
		int totalSize = findCount();
		return findPageByCriteria(criteria, pageNo, pageSize, totalSize);
	}
	
	@Override
	public PageBean<T> findPageByProperties(String[] propertyNames, Object[] values, int pageNo, int pageSize){
		if(propertyNames.length != values.length){
			throw new IllegalArgumentException("传入的属性名和属性值数量不一致");
		}
		if(Arrays.asList(propertyNames).contains(null)) throw new IllegalArgumentException("属性名不可能为null");
		
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(entityClass);
		Root<T> root = criteria.from(entityClass);
		criteria.select(root);
		Predicate predicate = null;
		for(int i=0; i< propertyNames.length; i++){
			Predicate condition = builder.equal(_getPath(root, propertyNames[i]), values[i]);
			predicate =(predicate == null)?condition:builder.and(predicate,condition);
		}
		criteria.where(predicate);
		
		//查询数量
//		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
//		countCriteria.from(entityClass);
//		countCriteria.select(builder.count(root));
//		countCriteria.where(predicate);
//		Long totalSize = getSession().createQuery(countCriteria).getSingleResult();
		int totalSize = findCountByProperties(propertyNames, values);
		
		return  findPageByCriteria(criteria, pageNo, pageSize, totalSize);
	}
	
	@Override
	public PageBean<T> findPageByProperty(String propertyName, Object value, int pageNo, int pageSize){
		if(!StringUtils.hasLength(propertyName)) throw new IllegalArgumentException("propertyName 不能为空" );
		String[] properties = new String[]{propertyName};
		Object[] values = new Object[]{value};
		return findPageByProperties(properties, values, pageNo, pageSize);
	}
	
	private <X> Path<?> _getPath(Path<X> path, String propertyName){
		Path<?> result;
		if(propertyName.contains(".")){
			String[] properties = propertyName.split("\\.");
			result = path.get(properties[0]);
			for(int i=1;i<properties.length; i++){
				result=result.get(properties[i]);
			}
		}else {
			result = path.get(propertyName);
		}
		return result;
	}

}

