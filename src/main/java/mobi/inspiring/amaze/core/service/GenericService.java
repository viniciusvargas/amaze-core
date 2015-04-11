package mobi.inspiring.amaze.core.service;

import java.io.Serializable;
import java.util.List;

import mobi.inspiring.amaze.core.eao.GenericEao;

public abstract class GenericService<T, PK extends Serializable> {
	
	private GenericEao<T, PK> genericEao;
	Class<T> clazz;
	
	public GenericService(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	public GenericEao<T, PK> getGenericEao() {
		if(this.genericEao == null)
			this.genericEao = new GenericEao<T, PK>();
			
		return genericEao;
	}
	
	public T findOneByFilter(T entity) {
		return this.getGenericEao().findOneByFilter(entity);
	}
	
	public List<T> findByFilter(T entity) {
		return this.getGenericEao().findByFilter(entity);
	}
	
	public T findById(PK id) {
		T entity = this.getGenericEao().findById(this.clazz, id);
		
		return entity;
	}
	
	public T save(T entity) {
		return this.getGenericEao().save(entity);
	}
	
	public void delete(T entity) {
		this.getGenericEao().delete(entity);
	}
	
	public T remove(T entity) {
		return this.getGenericEao().remove(entity);
	}
	
	public void update(T entity) {
		this.getGenericEao().update(entity);
	}
	
}
