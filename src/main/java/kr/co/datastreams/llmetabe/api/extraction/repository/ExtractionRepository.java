package kr.co.datastreams.llmetabe.api.extraction.repository;

import kr.co.datastreams.llmetabe.api.extraction.domain.ExtractionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtractionRepository extends JpaRepository<ExtractionEntity, Long> {
}
