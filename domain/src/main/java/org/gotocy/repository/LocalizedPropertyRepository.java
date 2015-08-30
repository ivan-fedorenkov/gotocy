package org.gotocy.repository;

import org.gotocy.domain.LocalizedProperty;
import org.gotocy.domain.Location;
import org.gotocy.domain.PropertyStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

/**
 * TODO: query on each method
 * @author ifedorenkov
 */
public interface LocalizedPropertyRepository extends JpaRepository<LocalizedProperty, Long>,
	QueryDslPredicateExecutor<LocalizedProperty>
{

	@Query("select lp from LocalizedProperty lp join fetch lp.property join fetch lp.property.imageSet.representativeImage " +
		"left join fetch lp.property.panoXml where lp.property.id = ?1 and lp.locale = ?2")
	LocalizedProperty findProperty(Long propertyId, String locale);

	List<LocalizedProperty> findByPropertyPropertyStatusAndLocale(PropertyStatus propertyStatus, String locale, Pageable pageable);

	List<LocalizedProperty> findByLocale(String locale, Pageable pageable);

	@Query("select lp from LocalizedProperty lp join fetch lp.property join fetch lp.property.imageSet.representativeImage " +
		"left join fetch lp.property.panoXml where lp.property.propertyStatus = ?1 and lp.property.location = ?2 and lp.locale = ?3")
	List<LocalizedProperty> findSimilar(PropertyStatus propertyStatus,
		Location location, String locale, Pageable pageable);

}
