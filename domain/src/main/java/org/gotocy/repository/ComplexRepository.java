package org.gotocy.repository;

import org.gotocy.domain.Complex;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ifedorenkov
 */
public interface ComplexRepository extends JpaRepository<Complex, Long> {

	@Override
	@EntityGraph(value = "Complex.withRequiredAssociations", type = EntityGraph.EntityGraphType.FETCH)
	List<Complex> findAll();

}
