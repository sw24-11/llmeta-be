package kr.co.datastreams.llmetabe.api.extraction.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataType {
    IMAGE("image"),
    PAPER("paper");

    private final String value;
}
