package org.gotocy.repository;

import org.gotocy.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ifedorenkov
 */
public interface PageRepository extends JpaRepository<Page, Long> {

	Page findByUrl(String url);

}
