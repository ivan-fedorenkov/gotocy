package org.gotocy.repository;

import org.gotocy.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * @author ifedorenkov
 */
public interface PageRepository extends JpaRepository<Page, Long>, QueryDslPredicateExecutor<Page> {

	@Query(name = "Page.findByUrl", nativeQuery = true)
	Page findByUrl(String url);

}
