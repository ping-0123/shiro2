package com.github.zhangkaitao.shiro.chapter19.dao;

import java.io.Serializable;
import java.util.List;

import com.github.zhangkaitao.shiro.chapter19.entity.PageBean;
import com.github.zhangkaitao.shiro.chapter19.exception.DataNotFoundException;



public interface BaseDao<T , PK extends Serializable> {

		public T get(PK id);
		public PK save(T entity);
		public void update(T entity);
		public void saveOrUpdate(T entity);
		public void delete(T entit);
		public void delete(PK id);
		public List<T> findAll();
		public int findCount();
		public List<T> findByProperty(String propertyName, Object value);
		public int findCountByProperty(String propertyName, Object value);
		public List<T> findByProperties(String[] propertyNames, Object[] values);
		public int findCountByProperties(String[] propertyNames, Object[] values);
		
		/**
		 * 濡傛灉T涓殑鏌愪竴鎴愬憳鍙橀噺鐨刢lass涓篅Entity娉ㄨВ, 鍒欐煡璇㈡椂蹇界暐璇ュ睘鎬э紝 鍗虫煡璇㈣鍙ユ病鏈夎〃鐨勫叧鑱�
		 * 
		 * @param entity
		 * @return
		 * @throws DataNotFoundException
		 */
		public List<T> findByExample(T entity);
		PageBean<T> findPageByHql(String hql, int pageNum, int pageSize);
		
		

}
