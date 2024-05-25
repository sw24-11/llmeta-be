package kr.co.datastreams.llmetabe.api.extraction.domain;

import jakarta.persistence.PrePersist;
import java.sql.Timestamp;

public class ExtractionEntityListener {

    @PrePersist
    public void prePersist(ExtractionEntity entity) {
        entity.setCreateAt(new Timestamp(System.currentTimeMillis()));
    }

}
