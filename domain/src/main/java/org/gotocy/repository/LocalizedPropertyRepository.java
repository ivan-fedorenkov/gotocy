package org.gotocy.repository;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import org.gotocy.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Locale;

import static org.gotocy.domain.QLocalizedProperty.localizedProperty;

/**
 * @author ifedorenkov
 */
public interface LocalizedPropertyRepository extends JpaRepository<LocalizedProperty, Long>,
	QueryDslPredicateExecutor<LocalizedProperty>, LocalizedPropertyRepositoryCustom
{

	@EntityGraph(value = "LocalizedProperty.withProperty", type = EntityGraph.EntityGraphType.LOAD)
	LocalizedProperty findByPropertyIdAndLocale(Long propertyId, String locale);

	@EntityGraph(value = "LocalizedProperty.withProperty", type = EntityGraph.EntityGraphType.LOAD)
	List<LocalizedProperty> findByPropertyPropertyStatusAndLocale(PropertyStatus propertyStatus, String locale, Pageable pageable);

	@EntityGraph(value = "LocalizedProperty.withProperty", type = EntityGraph.EntityGraphType.LOAD)
	Iterable<LocalizedProperty> findAll(Predicate predicate);

}
