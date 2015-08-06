package org.gotocy.repository;

import org.gotocy.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ifedorenkov
 */
public interface ImageRepository extends JpaRepository<Image, Long> {
}
