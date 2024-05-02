package kr.co.datastreams.llmetabe.api.evaluation.repository;

import kr.co.datastreams.llmetabe.api.evaluation.domain.EvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepository extends JpaRepository<EvaluationEntity, Long> {
}
