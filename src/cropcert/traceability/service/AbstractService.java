package cropcert.traceability.service;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import cropcert.traceability.dao.AbstractDao;


public abstract class  AbstractService<T> {

	public Class<T> entityClass;
	protected  AbstractDao<T, Long> dao;
	
	public AbstractService(AbstractDao<T, Long> dao) {
		this.dao = dao;
		entityClass = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
	}

	public T save(T entity) {
		try {
			this.dao.save(entity);
			return entity;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public T saveOrUpdate(T entity) {
		try {
			this.dao.saveOrUpdate(entity);
			return entity;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public T update(T entity)  {
		try {
			this.dao.update(entity);
			return entity;
		} catch (RuntimeException re) {
			throw re;
		}

	}

	public T delete(Long id) {
		try {
			T entity = (T) this.dao.findById(id);
			this.dao.delete(entity);
			return entity;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public T findById(Long id) {
		try {
			T entity = (T) this.dao.findById(id);
			return entity;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<T> findAll(int limit, int offset) {
		try {
			List<T> entities = this.dao.findAll(limit, offset);
			return entities;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<T> findAll() {
		
		try {
			List<T> entities = this.dao.findAll();
			return entities;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public T findByPropertyWithCondtion(String property, Object value, String condition) {
		return dao.findByPropertyWithCondition(property, value, condition);
	}
	
	public List<T> getByPropertyWithCondtion(String property, Object value, String condition, int limit, int offset) {
		return dao.getByPropertyWithCondtion(property, value, condition, limit, offset);
	}
	
	public List<T> getByMultiplePropertyWithCondtion(String[] properties, Object[] values, Integer limit,
			Integer offset) {
		return dao.getByMultiplePropertyWithCondtion(properties, values, limit, offset);
	}
	
	protected boolean isDifferent(Object o1, Object o2) {
		if(o1 == null && o2 == null) 
			return false;
		if(o1 == null || o2 == null)
			return true;
		
		return o1.equals(o2);
	}
}
