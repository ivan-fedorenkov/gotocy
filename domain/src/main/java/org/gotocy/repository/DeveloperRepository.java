package org.gotocy.repository;

import org.gotocy.domain.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ifedorenkov
 */
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
}
