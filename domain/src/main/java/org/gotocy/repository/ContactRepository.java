package org.gotocy.repository;

import org.gotocy.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ifedorenkov
 */
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
