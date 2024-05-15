package kr.co.datastreams.llmetabe.api.evaluation.service;

import kr.co.datastreams.llmetabe.api.evaluation.dao.EvaluationDao;
import kr.co.datastreams.llmetabe.api.evaluation.dto.EvaluationRequestDto;
import kr.co.datastreams.llmetabe.api.extraction.dao.ExtractionDao;
import kr.co.datastreams.llmetabe.global.exception.DatabaseAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluationService {

    private final ExtractionDao extractionDao;
    private final EvaluationDao evaluationDao;

    public void evaluate(EvaluationRequestDto evaluationRequestDto) {
        try {
            evaluationDao.saveEvaluationEntity(extractionDao.getExtractionEntity(evaluationRequestDto.getExtractionId()), evaluationRequestDto);
        } catch (Exception e) {
            throw new DatabaseAccessException();
        }
    }
}
