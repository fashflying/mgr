package mgr.wfengine.repository;

import java.io.Serializable;
import java.util.List;

public interface Repository<T, PK extends Serializable> {
	T get(PK id);
	void delete(T entity);
	void save(T entity);
	List<T> listAll();
}
