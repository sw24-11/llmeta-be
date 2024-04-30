package kr.co.datastreams.llmetabe.api.extraction.repository;

import kr.co.datastreams.llmetabe.api.extraction.domain.ExtractionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtractionRepository extends JpaRepository<ExtractionEntity, Long> {
    List<ExtractionEntity> findByMember(MemberEntity member); // TODO : Implement this class
}
