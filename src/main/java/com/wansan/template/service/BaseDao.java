package com.wansan.template.service;

import com.wansan.template.core.Utils;
import com.wansan.template.model.BasePojo;
import com.wansan.template.model.OperEnum;
import com.wansan.template.model.Person;
import com.wansan.template.model.Syslog;
import org.hibernate.*;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Administrator on 14-4-15.
 */
@SuppressWarnings("unchecked")
public abstract class BaseDao<T extends BasePojo> implements IBaseDao<T>
{
    private Class<T> clazz;
    private String className;
    @Resource
    protected SessionFactory sessionFactory;

    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    public BaseDao()
    {
        this.clazz = null;
        Class c = getClass();
        Type t = c.getGenericSuperclass();
        if (t instanceof ParameterizedType)
        {
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            this.clazz = (Class<T>) p[0];
        }
        className = clazz.getSimpleName();
    }

    public List<T> findByName(String name)
    {
        return (List<T>)getSession().createQuery(String.format("from %s where name like '%%%s%%'", className, name)).list();
    }

    @Override
    public Serializable save(T entity)
    {
        if (entity.getId() == null)
            entity.setId(Utils.getNewUUID());
        if (entity.getCreatetime() == null)
            entity.setCreatetime(Utils.getNow());
        return getSession().save(entity);
    }

    @Override
    public void saveOrUpdate(T entity,boolean ct)
    {
        if(ct){
        if(entity.getId() == null)
            save(entity);
        else {
            entity.setCreatetime(Utils.getNow());
            getSession().saveOrUpdate(entity);
        }}
        else
            getSession().saveOrUpdate(entity);

    }

    public void saveOrUpdate(T entity){
        saveOrUpdate(entity,true);
    }
    @Override
    public void saveOrUpdate(Collection<T> entities)
    {
        Session session = getSession();
        for(T item:entities)
            session.saveOrUpdate(item);

    }

    @Override
    public void delete(T entity)
    {
        getSession().delete(entity);

    }

    public void delete(Serializable id)
    {
        T entity = this.load(id);
        delete(entity);

    }

    public void delete(Collection<T> entities)
    {
        for(T item:entities)
            getSession().delete(item);
    }

    public void delete(String idList)
    {
        String[] ids = idList.split(",");
        for(String id:ids){
            T item = load(id);
            getSession().delete(item);
        }
    }


    public T load(Serializable id)
    {

        return (T) getSession().load(clazz, id);
    }

    @Override
    public List publicFind(String queryString)
    {
        return getSession().createQuery(queryString).list();
    }


    public List<T> find(String queryString)
    {
        return (List<T>) getSession().createQuery(queryString).list();
    }


    public List<T> find(T example) throws HibernateException
    {
        Criteria criteria = getSession().createCriteria(clazz);
        return criteria.add(Example.create(example)).list();
    }


    public T findById(Serializable id) throws HibernateException
    {
        return (T) getSession().get(clazz, id);
    }



    public List<T> find(String hql, Object obj) throws HibernateException
    {
        Query query = getSession().createQuery(hql);
        query.setParameter(0, obj);
        return (List<T>)query.list();
    }


    public List<T> findInCollectionByPage(final String hql, final List value,
                                          final int offset, final int pageSize) throws HibernateException
    {

        Query query = getSession().createQuery(hql);
        if (null != value){
            query.setParameterList("paraList", value);
        }
        if(offset>1)
            return query.setFirstResult(offset) .setMaxResults(pageSize).list();
        else
            return query.list();
    }


    public List<T> findByProperty(String fieldName, Object value) throws HibernateException
    {
        String sql = "from " + className + " where " + fieldName + "=?";
        return find(sql, value);
    }

    public int executeQuery(final String hql) throws HibernateException
    {

        Query query = getSession().createQuery(hql);
        return query.executeUpdate();
    }

    public Object uniqueResult(final String hql) throws HibernateException
    {

        Query query = getSession().createQuery(hql);
        Object result = query.uniqueResult();
        return result;
    }

    public Long getCount() throws HibernateException
    {
        return (Long) uniqueResult("select count(*) from " + className);
    }

    public Long getCount(String hql) throws HibernateException
    {
        return (Long) uniqueResult(hql);
    }


    public List<T> listAll() throws HibernateException
    {
        return findByMapWithCond(null,null,null);
    }

    public List findBySQL(final String sql) throws HibernateException
    {
        Query query = getSession().createSQLQuery(sql);
        return query.list();
    }



/*
    protected final Serializable saveLog(SyslogEntity log) throws HibernateException
    {
        log.setId(Utils.getNewUUID());
        log.setCreatetime(Utils.getNow());
        return getSession().save(log);
    }
*/

    public Long getCount(String hql, final Object[] values)
            throws HibernateException
    {
        String countHql = "select count(*) "
                + hql.substring(hql.indexOf("from"));
        return (Long) uniqueResult(countHql, values);
    }

    public Object uniqueResult(final String hql, final Object[] values)
            throws HibernateException {
        Query query = getSession().createQuery(hql);
        if (null != values && values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        Object result = query.uniqueResult();
        return result;
    }

    public List<T> findByMap(String hql,final Map<String,Object> params){
//        Query query = getSession().createQuery(hql);
//        for(String field:params.keySet())
//            query.setParameter(field,params.get(field));
        Criteria criteria = getSession().createCriteria(clazz);
        criteria.add(Restrictions.allEq(params));
        return criteria.list();
    }

    public final Map<String,Object> findByMap(final Map<String,Object> params,int page,int rows,String order,boolean asc) throws HibernateException{
        Criteria criteria = getSession().createCriteria(clazz);
        Long total;
            if(null!=params)
            criteria.add(Restrictions.allEq(params));
        total = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        criteria.setProjection(null).setResultTransformer(Criteria.ROOT_ENTITY);
        if(page>0&&rows>0)
            criteria.setFirstResult((page-1)*rows).setMaxResults(rows);
        if(null!=order){
            if(asc)
                criteria.addOrder(Order.asc(order));
            else
                criteria.addOrder(Order.desc(order));
        }
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("total",total);
        result.put("rows",(List<T>)criteria.list());
        return result;
    }

    public Map<String,Object> findByMapWithCond(final Map<String,Object> params,int page,int rows,String order,boolean asc) throws HibernateException{
        StringBuilder hql = new StringBuilder( "from "+className );
        Query query,countQuery;
        Long total;
        Map<String,Object> alterParams = new HashMap<>();
        if(null!=params&&!params.isEmpty()){
            hql.append(" where ");
            for(String field:params.keySet()){
                int i = field.indexOf(" ");
                Assert.isTrue(i != -1, "Wrong condition, must have space inside!");
                String ramdonName = "_" + Utils.getRandomString(8);
                hql.append(field).append(" :").append(ramdonName).append(" and ");
                alterParams.put(ramdonName,params.get(field));
            }
            hql.append("1=1");
            countQuery = getSession().createQuery("SELECT count(*) "+hql.toString());
            if(null!=order){
                hql.append(" order by ").append( order );
                if(asc)
                    hql.append(" asc");
                else
                    hql.append(" desc");
            }
            query = getSession().createQuery(hql.toString());

            for(String field:alterParams.keySet()){
                query.setParameter(field,alterParams.get(field));
                countQuery.setParameter(field,alterParams.get(field));
            }
        } else {
            countQuery = getSession().createQuery("SELECT count(*) "+hql.toString());
            if (null != order) {
                hql.append(" order by ").append(order);
                if (asc)
                    hql.append(" asc");
                else
                    hql.append(" desc");
            }
            query = getSession().createQuery(hql.toString());

        }
        total = (Long) countQuery.uniqueResult();
        if(page>0&&rows>0){
            query.setFirstResult((page-1)*rows).setMaxResults(rows);
        }
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("total",total);
        result.put("rows",query.list());
        return result;
    }

    public Serializable txSave(T entity,Person person){
        String id = (String) save(entity);
        Syslog syslog = new Syslog();
        syslog.setUserid(person.getName());
        syslog.setName(OperEnum.CREATE.toString());
        syslog.setComment("用户"+person.getName()+"添加"+className+"-->"+entity.getName());
        syslog.setCreatetime(Utils.getNow());
        syslog.setId(Utils.getNewUUID());
        getSession().save(syslog);
        return id;
    }

    public void txUpdate(T entity,Person oper){
        saveOrUpdate(entity);
        Syslog syslog = new Syslog();
        syslog.setUserid(oper.getName());
        syslog.setName(OperEnum.UPDATE.toString());
        syslog.setComment("用户"+oper.getName()+"修改"+className+"-->"+entity.getName());
        syslog.setCreatetime(Utils.getNow());
        syslog.setId(Utils.getNewUUID());
        getSession().save(syslog);
    }

    public void txDelete(String idList,Person oper){
        delete(idList);
        Syslog syslog = new Syslog();
        syslog.setUserid(oper.getName());
        syslog.setName(OperEnum.DELETE.toString());
        syslog.setComment("用户"+oper.getName()+"删除"+className);
        syslog.setCreatetime(Utils.getNow());
        syslog.setId(Utils.getNewUUID());
        getSession().save(syslog);
    }

    //默认顺序createtime desc
    public List<T> findByMapWithCond(Map<String,Object> params,String orderBy,Boolean asc){
        StringBuilder hql = new StringBuilder( "from "+className );
        String orderStr;
        if(null==orderBy){
            orderStr = " order by createtime desc";
        }else {
            String ascStr = asc?" asc":" desc";
            orderStr = " order by "+ orderBy + ascStr;
        }
        Map<String,Object> alterParams = new HashMap<>();
        Query query;
        if(null!=params&&!params.isEmpty()){
            hql.append(" where ");
            for(String field:params.keySet()){
                int i = field.indexOf(" ");
                Assert.isTrue(i != -1, "Wrong condition, must have space inside!");
                String ramdonName = "_" + Utils.getRandomString(8);
                hql.append(field).append(" :").append(ramdonName).append(" and ");
                if(field.contains("like")){
                    String likeStr = "%"+params.get(field)+"%";
                    alterParams.put(ramdonName,likeStr);
                }else
                    alterParams.put(ramdonName,params.get(field));
            }
            hql.append("1=1");
            query = getSession().createQuery(hql.append(orderStr).toString());

            for(String field:alterParams.keySet())
                query.setParameter(field,alterParams.get(field));

        }else
            query = getSession().createQuery(hql.append(orderStr).toString());
        return query.list();
    }
}
