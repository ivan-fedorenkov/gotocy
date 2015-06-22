package org.gotocy.persistance;

import com.mysema.query.SearchResults;

/**
 * @author ifedorenkov
 */
public interface DaoBase<T> {

	T save(T entity);

	T findOne(Long id);
	SearchResults<T> findAll();

}
