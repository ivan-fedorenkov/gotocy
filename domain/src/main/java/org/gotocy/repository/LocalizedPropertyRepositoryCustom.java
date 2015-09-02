package org.gotocy.repository;

import com.mysema.query.types.Predicate;
import org.gotocy.domain.LocalizedProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author ifedorenkov
 */
public interface LocalizedPropertyRepositoryCustom {

	Page<LocalizedProperty> findAll(Predicate predicate, Pageable pageable);

}
