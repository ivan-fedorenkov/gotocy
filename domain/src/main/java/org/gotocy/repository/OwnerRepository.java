package org.gotocy.repository;

import org.gotocy.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ifedorenkov
 */
public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
