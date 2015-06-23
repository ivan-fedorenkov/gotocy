package org.gotocy.repository;

import org.gotocy.domain.LocalizedProperty;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ifedorenkov
 */
public interface LocalizedPropertyRepository extends JpaRepository<LocalizedProperty, Long> {
}
