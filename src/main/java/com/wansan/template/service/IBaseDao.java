package com.wansan.template.service;

import com.wansan.template.model.Person;
import org.hibernate.HibernateException;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 14-4-15.
 */
public interface IBaseDao<T> {
    public void saveOrUpdate(T entity,boolean ct);
    public List<T> findByName(String name) ;
    public List publicFind(String queryString) ;
    public Serializable save(T entity) ;
    public void saveOrUpdate(T entity);
    public void saveOrUpdate(Collection<T> entities) ;
    public void delete(T entity);
    public void delete(Serializable id);
    public void delete(Collection<T> entities);
    public T load(Serializable id);
    public List<T> find(String queryString);
    public List<T> find(T example) ;
    public T findById(Serializable id) ;
    public List<T> find(String hql,Object obj);
    public List<T> findByProperty(String fieldName,Object value);
    public int executeQuery(final String hql) ;
    public Object uniqueResult(final String hql) ;
    public List<T> listAll() ;
    public List findBySQL(final String sql) ;
    public Long getCount() ;
    public Long getCount(String hql) ;
    public void delete(String idList);
    public Map<String,Object> findByMap(Map<String,Object> params,int page,int rows,String order,boolean asc) throws HibernateException;
    public Map<String,Object> findByMapWithCond(Map<String,Object> params,int page,int rows,String order,boolean asc) throws HibernateException;

    public List<T> findByMapWithCond(Map<String,Object> params,String orderBy,Boolean asc);
    public List<T> findByMap(String hql,final Map<String,Object> params);

    public Serializable txSave(T entity,Person person);
    public void txUpdate(T entity,Person oper);
    public void txDelete(String idList,Person oper);

}