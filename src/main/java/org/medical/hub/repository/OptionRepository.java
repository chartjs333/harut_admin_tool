package org.medical.hub.repository;

import org.medical.hub.models.Options;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OptionRepository extends JpaRepository<Options, Long> {

    Optional<Options> findByName(String name);
}
