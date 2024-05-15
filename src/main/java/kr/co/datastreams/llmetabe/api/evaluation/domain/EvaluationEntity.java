package kr.co.datastreams.llmetabe.api.evaluation.domain;

import jakarta.persistence.*;
import java.sql.Timestamp;
import kr.co.datastreams.llmetabe.api.evaluation.dto.EvaluationRequestDto;
import kr.co.datastreams.llmetabe.api.extraction.domain.ExtractionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "evaluation")
public class EvaluationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_id", nullable = false)
    private Long evaluationId;

    @ManyToOne
    @JoinColumn(name = "extraction_id")
    private ExtractionEntity extraction;

    @Column(name = "rate", nullable = false)
    private Double rate;

    @Column(name = "feedback", nullable = false)
    private String feedback;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    public EvaluationEntity(ExtractionEntity extractionEntity, EvaluationRequestDto evaluationRequestDto) {
        this.extraction = extractionEntity;
        this.rate = evaluationRequestDto.getRate();
        this.feedback = evaluationRequestDto.getFeedback();
    }
}
