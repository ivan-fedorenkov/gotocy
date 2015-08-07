package org.gotocy.repository;

import org.gotocy.domain.Image;
import org.gotocy.domain.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ifedorenkov
 */
public interface ImageRepository extends JpaRepository<Image, Long> {
}
