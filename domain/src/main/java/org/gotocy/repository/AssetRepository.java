package org.gotocy.repository;

import org.gotocy.domain.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ifedorenkov
 */
public interface AssetRepository extends JpaRepository<Asset, Long> {
}
