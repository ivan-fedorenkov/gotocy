package org.gotocy.repository;

import org.gotocy.domain.LocalizedProperty;
import org.gotocy.domain.Location;
import org.gotocy.domain.PropertyStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * TODO: query on each method
 * @author ifedorenkov
 */
public interface LocalizedPropertyRepository extends JpaRepository<LocalizedProperty, Long> {

	@Query("select lp from LocalizedProperty lp join fetch lp.property join fetch lp.property.imageSet.representativeImage " +
		"where lp.property.id = ?1 and lp.locale = ?2")
	LocalizedProperty findProperty(Long propertyId, String locale);

	List<LocalizedProperty> findByPropertyPropertyStatusAndLocale(PropertyStatus propertyStatus, String locale);

	@Query("select lp from LocalizedProperty lp join fetch lp.property join fetch lp.property.imageSet.representativeImage " +
		"where lp.property.propertyStatus = ?1 and lp.property.location = ?2 and lp.locale = ?3")
	List<LocalizedProperty> findSimilar(PropertyStatus propertyStatus,
		Location location, String locale, Pageable pageable);

}
