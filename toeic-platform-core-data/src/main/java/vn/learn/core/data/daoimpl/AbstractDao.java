package vn.learn.core.data.daoimpl;

import org.hibernate.*;
import vn.learn.core.common.constant.CoreConstant;
import vn.learn.core.common.utils.HibernateUtil;
import vn.learn.core.data.dao.GenericDao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public  class AbstractDao<ID extends Serializable,T> implements GenericDao<ID,T> {
    private Class<T> persistenceClass;
    public AbstractDao()
    {
        this.persistenceClass=(Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }
    protected Session getSession()
    {
        return HibernateUtil.getSessionFactory().openSession();
    }
    private String getPersistenceClassName()
    {
        return persistenceClass.getSimpleName();
    }
    public List findAll() {
        List<T> list=new ArrayList<T>();
        Session session=getSession();
        Transaction transaction=session.beginTransaction();
        try
        {
            StringBuilder stringBuilder=new StringBuilder("from ");
            stringBuilder.append(this.getPersistenceClassName());
            Query query=session.createQuery(stringBuilder.toString());
            list=query.list();
            transaction.commit();
        }
        catch (HibernateException e){
            transaction.rollback();
            throw e;
        }
        finally {
            session.close();
        }
        return list;
    }

    public T update(T entity) {
        T result=null;
        Session session=getSession();
        Transaction transaction=session.beginTransaction();
        try{
        Object object=session.merge(entity);
        result =(T)object;
        transaction.commit();

        } catch(HibernateException e){
            transaction.rollback();
            throw e;
        }
        finally {
            session.close();
        }
        return result;
    }

    public void save(T entity) {
        Session session=getSession();
        Transaction transaction=session.beginTransaction();
        try{
            session.persist(entity);
            transaction.commit();
        }catch (HibernateException e)
        {
            transaction.rollback();
            throw  e;
        }
        finally {
            session.close();
        }

    }

    public T findById(ID id) {
        Session session=getSession();
        T result=null;
        Transaction transaction= session.beginTransaction();
        try{
            result= (T) session.get(persistenceClass,id);
            transaction.commit();
            if(result==null) {
                throw new ObjectNotFoundException("NOT FOUND"+id ,null);
            }
        }
        catch (HibernateException e){
            transaction.rollback();
            throw e;
        }
        finally {
            session.close();
        }

        return result;
    }

    public Object findByProperty(String property, Object value, String sortExpression, String sortDirection) {
        List<T> list=new ArrayList<T>();
        Session session=getSession();
        Transaction transaction=session.beginTransaction();
        Object totalItem=0;
        try{
            StringBuilder sql1=new StringBuilder("from ");
            sql1.append(getPersistenceClassName());
            if(property!=null&&value!=null)
            {
                sql1.append(" where ").append(property).append("= :value");
            }
            if(sortDirection!=null&&sortExpression!=null)
            {
                sql1.append(" order by ").append(sortExpression);
                sql1.append(" "+(sortDirection.equals(CoreConstant.SORT_ASC)?"asc":"desc"));
            }
            Query query1=session.createQuery(sql1.toString());
            if(value!=null&&property!=null)
            {
                query1.setParameter("value",value);
            }
            list=query1.list();
            StringBuilder sql2=new StringBuilder("select count(*) from ");
            sql2.append(getPersistenceClassName());
            if(value!=null&&property!=null)
            {
                sql2.append(" where "+property+"= :value");
            }
            if(sortDirection!=null&&sortExpression!=null)
            {
                sql2.append(" order by ").append(sortExpression);
                sql2.append(" ").append(sortDirection.equals(CoreConstant.SORT_ASC)?"asc":"desc");
            }
            Query query2=session.createQuery(sql2.toString());
            if(value!=null&&property!=null)
            {
                query2.setParameter("value",value);
            }
            totalItem=query2.list().get(0);
            transaction.commit();
        }
        catch (HibernateException e){
                transaction.rollback();
                throw e;
        }
        finally {
            session.close();
        }
        return new Object[]{totalItem,list};
    }

    public int delete(List<ID> list) {
        Session session=getSession();
        int count=0;
        Transaction transaction=session.beginTransaction();
        try{

            for(ID quet:list) {
                T object= (T) session.get(persistenceClass,quet);
                session.delete(object);
                count++;
            }
            transaction.commit();
        }
        catch (HibernateException e){
            transaction.rollback();
            throw e;
        }
        finally {
            session.close();
        }


        return count;
    }

}
