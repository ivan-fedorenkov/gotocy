package org.gotocy.repository;

import org.gotocy.domain.LocalizedProperty;
import org.gotocy.domain.LocalizedPropertySpecification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ifedorenkov
 */
public interface LocalizedPropertySpecificationRepository extends JpaRepository<LocalizedPropertySpecification, Long> {
	List<LocalizedPropertySpecification> findByLocalizedProperty(LocalizedProperty localizedProperty);
}
