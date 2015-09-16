package org.gotocy.repository;

import com.mysema.query.types.Predicate;
import org.gotocy.domain.Property;
import org.gotocy.domain.PropertyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * @author ifedorenkov
 */
public interface PropertyRepository extends JpaRepository<Property, Long>, QueryDslPredicateExecutor<Property> {

	@EntityGraph(value = "Property.withAssets", type = EntityGraph.EntityGraphType.FETCH)
	Page<Property> findByPropertyStatus(PropertyStatus propertyStatus, Pageable pageable);

}
