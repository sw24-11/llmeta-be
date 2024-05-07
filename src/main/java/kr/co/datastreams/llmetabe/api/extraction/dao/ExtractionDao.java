package kr.co.datastreams.llmetabe.api.extraction.dao;

import kr.co.datastreams.llmetabe.api.extraction.domain.ExtractionEntity;
import kr.co.datastreams.llmetabe.api.extraction.repository.ExtractionRepository;
import kr.co.datastreams.llmetabe.api.member.domain.MemberEntity;
import kr.co.datastreams.llmetabe.global.exception.DatabaseAccessException;
import kr.co.datastreams.llmetabe.global.exception.NoSearchResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ExtractionDao {
    private final ExtractionRepository  extractionRepository;

    public ExtractionEntity getExtractionEntity(Long extractionId) {
        return extractionRepository.findById(extractionId).orElseThrow(NoSearchResultException::new);
    }

    public List<ExtractionEntity> getExtractionEntitiesByMember(MemberEntity member) {
        return extractionRepository.findByMember(member);
    }

    public void saveExtractionEntity(ExtractionEntity extractionEntity) {
        try {
            extractionRepository.save(extractionEntity);
        } catch (Exception e) {
            throw new DatabaseAccessException();
        }
    }
}
