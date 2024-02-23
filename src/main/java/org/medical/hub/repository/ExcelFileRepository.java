package org.medical.hub.repository;

import org.medical.hub.models.ExcelFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ExcelFileRepository extends JpaRepository<ExcelFile, Long> {

    Optional<ExcelFile> findByIdAndUserId(Long excelFileId, Long userId);

    Page<ExcelFile> findByUserId(Long userId, Pageable page);
}
