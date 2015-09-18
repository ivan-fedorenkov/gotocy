package org.gotocy.repository;

import org.gotocy.domain.Complex;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ifedorenkov
 */
public interface ComplexRepository extends JpaRepository<Complex, Long> {
}
