package kr.co.datastreams.llmetabe.api.evaluation.dao;

import kr.co.datastreams.llmetabe.api.evaluation.domain.EvaluationEntity;
import kr.co.datastreams.llmetabe.api.evaluation.dto.EvaluationRequestDto;
import kr.co.datastreams.llmetabe.api.evaluation.repository.EvaluationRepository;
import kr.co.datastreams.llmetabe.global.exception.DatabaseAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EvaluationDao {

    private final EvaluationRepository evaluationRepository;

    public void saveEvaluationEntity(EvaluationRequestDto evaluationRequestDto) {
        try {
            evaluationRepository.save(new EvaluationEntity(evaluationRequestDto));
        } catch (Exception e) {
            throw new DatabaseAccessException();
        }
    }
}