package mgr.wfengine.repository.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import mgr.wfengine.repository.Repository;

import org.hibernate.Session;

public abstract class HibernateRepository<T, PK extends Serializable>  implements Repository<T, PK>{
	protected Session getCurrentSession(String database){
		return HibernateUtil.getSessionFactory(database).getCurrentSession();
	}
	private Class<T> entityClass = null;
	protected String database;
	
	@SuppressWarnings("unchecked")
	public HibernateRepository(String database){
		Class<?> c = getClass();
        Type t = c.getGenericSuperclass();
        if (t instanceof ParameterizedType) {//TODO 当不是继承的类时，这里有问题
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            this.entityClass = (Class<T>) p[0];
        }
        
        this.database = database;
	}
	@SuppressWarnings("unchecked")
	@Override
	public T get(PK id) {
		try{
			return (T)getCurrentSession(this.database).get(this.entityClass, id);
		} catch (Exception exp){
			exp.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> listAll(){
		return getCurrentSession(this.database).createQuery("from " + this.entityClass.getName()).list();
	}
	
	public void save(T entity){
		getCurrentSession(this.database).save(entity);
	}

	@Override
	public void delete(T entity) {
		getCurrentSession(this.database).delete(entity);
	}
}
