package org.gotocy.repository;

import org.gotocy.domain.Property;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ifedorenkov
 */
public interface PropertyRepository extends JpaRepository<Property, Long> {
}
