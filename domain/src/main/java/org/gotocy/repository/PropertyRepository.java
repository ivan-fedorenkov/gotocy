package org.gotocy.repository;

import com.mysema.query.types.Predicate;
import org.gotocy.domain.Property;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * @author ifedorenkov
 */
public interface PropertyRepository extends JpaRepository<Property, Long>, QueryDslPredicateExecutor<Property> {

	@EntityGraph(value = "Property.withAssociations", type = EntityGraph.EntityGraphType.FETCH)
	Iterable<Property> findAll(Predicate predicate, Sort sort);

}
