package kr.co.datastreams.llmetabe.global.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    DATABASE_ERROR("데이터베이스에서 오류가 발생하였습니다."),
    NO_SEARCH_RESULT("데이터베이스에 조회 결과가 없습니다.");

    private final String message;
}