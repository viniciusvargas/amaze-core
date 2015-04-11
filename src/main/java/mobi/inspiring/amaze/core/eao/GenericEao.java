package mobi.inspiring.amaze.core.eao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import mobi.inspiring.amaze.core.eao.GenericEao;
import mobi.inspiring.amaze.core.jpa.JPA;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

public class GenericEao<T, PK extends Serializable> {

	@SuppressWarnings("unchecked")
	public T findOneByFilter(T entity) {
		Example e = Example.create(entity);
		List<T> result = null;
		Session session = (Session) JPA.em().getDelegate(); 
		
		Criteria crit = session.createCriteria(entity.getClass());
		crit.add(e);
		result = (List<T>)crit.list();
		
		if(result.isEmpty())
			return null;
		else
			return result.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findByFilter(T entity) {
		Session session = (Session) JPA.em().getDelegate();
		Criteria crit = session.createCriteria(entity.getClass());
		crit.add(Example.create(entity));
		
		List<T> listResult = (List<T>) crit.list();
		
		return listResult;
	}
	
	@SuppressWarnings("unchecked")
	public T findById(Class<? extends Object> clazz, PK id) throws NullPointerException {
		T result = null;
		EntityManager em = JPA.em();
		em.getTransaction().begin();
		
		result = (T) em.find(clazz, id);

		em.close();
		return result;
	}

	public T save(T entity) {
		EntityManager em = JPA.em();
		em.getTransaction().begin();
		
		em.persist(entity);
		em.getTransaction().commit();
		em.close();
		
		return entity;
	}
	
	public void delete(T entity) {
		EntityManager em = JPA.em();
		em.getTransaction().begin();
		
		em.remove(entity);
		em.getTransaction().commit();
		em.close();
	}
	
	public T remove(T entity) {
		EntityManager em = JPA.em();
		em.getTransaction().begin();
		
		em.remove(entity);
		em.getTransaction().commit();
		em.close();
		
		return entity;
	}
	
	public void update(T entity) {
		EntityManager em = JPA.em();
		em.getTransaction().begin();
		
		em.merge(entity);
		em.getTransaction().commit();
		em.close();
	}
	
	/*
	@SuppressWarnings({ "unchecked" })
	public List<T> findByFilterInCollection(Object entity) {
		Session session = (Session) JPA.em().getDelegate();
		Criteria crit = session.createCriteria(entity.getClass());
		crit.add(Example.create(entity));
		
		return (List<T>) crit.list();
	}
	
	public Object getById(Object entity, Long id) throws InstantiationException, IllegalAccessException {
		Object result = null;
		EntityManager em = JPA.em();
		em.getTransaction().begin();
		
		if (id > 0 && id != null) {
			result = entity.getClass().newInstance();
			result = em.find(entity.getClass(), id);
		}
		
		em.close();
		return result;
	}

	public <E> Integer countByExample(E entity) {
		Session session = (Session) JPA.em().getDelegate();
		Criteria crit = session.createCriteria(entity.getClass());
		crit.setProjection(Projections.rowCount());
		crit.add(Example.create(entity));
		
		Long result = (Long) crit.uniqueResult();
		return result.intValue();
	}
	
	public Integer countAll() {
		Session session = (Session) JPA.em().getDelegate();
		Long result;
		
		try {
			Criteria crit = session.createCriteria(getClass());
			crit.setProjection(Projections.rowCount());
			crit.add(Example.create(this));
			
			result = (Long) crit.uniqueResult();
		} catch (IndexOutOfBoundsException ex) {
			return null;
		}
		return result.intValue();
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<T> findByKeyValue(String key, String value, Object t) throws  SecurityException, IllegalArgumentException, IllegalAccessException, InstantiationException {
		Class<?> clazz = t.getClass();
		Field field;
		try {
			field = clazz.getDeclaredField(key);
			if(field != null) {
				Type type = field.getType();
				field.setAccessible(true);
				
				if(type.equals(String.class)) {
					field.set(t, value);
				}
				else if(type.equals(Double.class)) {
					field.set(t, Double.valueOf(value));
				}
				else if(type.equals(Integer.class)) {
					field.set(t, Integer.valueOf(value));
				} 
				else if(type.equals(Boolean.class)) {
					field.set(t, Boolean.valueOf(value));
				}
				else if(type.equals(Timestamp.class)) {
					field.set(t, Timestamp.valueOf(value));
				}
				else if((type.equals(List.class) || type.equals(Set.class)) && field.getGenericType() instanceof ParameterizedType) {

					EntityManager em = JPA.em();
					
					String queryString = "SELECT o FROM "+clazz.getSimpleName()+" o JOIN FETCH o."+key+" t WHERE t.id = :id";
					Query query = em.createQuery(queryString);
					query.setParameter("id", Long.valueOf(value));
					
					List<T> listResult = query.getResultList();
					em.close();
					
					return listResult;
				}
				
				return findByFilter(t);
			}
		} catch (NoSuchFieldException e) {
			clazz = clazz.getSuperclass();
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public void addToCollection(String idMaster, String attribute, String idSub) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InstantiationException {
		Class<T> typeMaster = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		Field field;
		field = typeMaster.getDeclaredField(attribute);
		ParameterizedType pt = (ParameterizedType) field.getGenericType();
		Class<T> subclass = (Class<T>) pt.getActualTypeArguments()[0];
		
		EntityManager em = JPA.em();
		
		Object master = typeMaster.cast(em.find(typeMaster, Long.valueOf(idMaster)));
		
		Object sub = em.find(subclass, Long.valueOf(idSub));
		
		String queryString = "SELECT DISTINCT o."+attribute+" FROM "+typeMaster.getSimpleName()+" o LEFT JOIN o."+attribute+" WHERE o.id = :id";
		Query query = em.createQuery(queryString);
		query.setParameter("id", Long.valueOf(idMaster));
		
		List<Object> listResult = query.getResultList();
		listResult.add(sub);
		
		em.close();
		
		field.setAccessible(true);
		field.set(master, listResult);
		
		update(master);
	}
	/*
	@SuppressWarnings("unchecked")
	public void addToUniqueCollection(String idMaster, String attribute, String idSub) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InstantiationException {
		Class<T> typeMaster = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		Field field;
		field = typeMaster.getDeclaredField(attribute);
		ParameterizedType pt = (ParameterizedType) field.getGenericType();
		Class<T> subclass = (Class<T>) pt.getActualTypeArguments()[0];
		
		EntityManager em = JPA.em();
		
		Object master = typeMaster.cast(em.find(typeMaster, Long.valueOf(idMaster)));
		
		Object sub = em.find(subclass, Long.valueOf(idSub));
		
		String queryString = "SELECT DISTINCT o."+attribute+" FROM "+typeMaster.getSimpleName()+" o LEFT JOIN o."+attribute+" WHERE o.id = :id";
		Query query = em.createQuery(queryString);
		query.setParameter("id", Long.valueOf(idMaster));
		
		List<Object> listResult = query.getResultList();
		if(!listResult.contains(sub))
			listResult.add(sub);
		
		em.close();
		
		field.setAccessible(true);
		field.set(master, listResult);
		
		update(master);
	}
	
	@SuppressWarnings("unchecked")
	public T fromJsonToEntity(String json, Class<?> t) {
		return (T) new GsonBuilder().create().fromJson(json, t);
	}*/

}
