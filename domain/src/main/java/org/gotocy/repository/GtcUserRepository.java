package org.gotocy.repository;

import org.gotocy.domain.GtcUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ifedorenkov
 */
public interface GtcUserRepository extends JpaRepository<GtcUser, Long> {

	GtcUser findByUsername(String username);

}
