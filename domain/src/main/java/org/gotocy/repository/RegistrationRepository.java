package org.gotocy.repository;

import org.gotocy.domain.Property;
import org.gotocy.domain.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ifedorenkov
 */
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

	Registration findByRelatedProperty(Property property);

}
