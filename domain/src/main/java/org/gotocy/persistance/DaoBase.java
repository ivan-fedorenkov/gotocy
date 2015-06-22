package org.gotocy.persistance;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author ifedorenkov
 */
public interface DaoBase<T> extends PagingAndSortingRepository<T, Long> {
}
