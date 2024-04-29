package kr.co.datastreams.llmetabe.api.extraction.domain;

import jakarta.persistence.*;
import kr.co.datastreams.llmetabe.api.extraction.enums.DataType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "extraction")
public class ExtractionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "extraction_id", nullable = false)
    private Long extractionId;

    @ManyToOne()
    @JoinColumn(name = "member_id")
    private MemberEntity member; // TODO : MemberEntity 추가 후 수정 필요

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 100)
    private DataType type;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_key")
    private String fileKey;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "meta_data", nullable = false)
    private String metaData;

    @Column(name = "create_at", nullable = false)
    private Timestamp createAt;

    // TODO : EvaluationEntity 추가 후 관계 작성 필요
}
