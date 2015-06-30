package org.gotocy.repository;

import org.gotocy.domain.LocalizedProperty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ifedorenkov
 */
public interface LocalizedPropertyRepository extends JpaRepository<LocalizedProperty, Long> {

	LocalizedProperty findByPropertyIdAndLocale(Long propertyId, String locale);

	List<LocalizedProperty> findPropertiesByLocale(String locale);

}
