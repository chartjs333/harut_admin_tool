package org.medical.hub.repository;

import org.medical.hub.models.CompareBetween;
import org.medical.hub.models.FileType;
import org.medical.hub.models.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RuleRepository extends JpaRepository<Rule, Long>, JpaSpecificationExecutor {

    List<Rule> findByFileType(FileType fileType);

    List<Rule> findByFileTypeAndCompareWith(FileType fileType, CompareBetween compareBetween);
}
