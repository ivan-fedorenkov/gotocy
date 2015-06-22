package org.gotocy.persistance;

import java.util.List;

/**
 * @author ifedorenkov
 */
public interface DaoBase<T> {

	T save(T entity);

	T findOne(Long id);
	List<T> findAll();

}
