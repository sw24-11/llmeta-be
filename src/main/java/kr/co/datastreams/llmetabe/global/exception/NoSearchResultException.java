package kr.co.datastreams.llmetabe.global.exception;

import kr.co.datastreams.llmetabe.global.advice.ErrorCode;
import kr.co.datastreams.llmetabe.global.advice.ErrorMessage;

public class NoSearchResultException extends GlobalException {

    public NoSearchResultException() {
        super(ErrorMessage.NO_SEARCH_RESULT.getMessage());
    }

    @Override
    public ErrorCode code() {
        return ErrorCode.D03;
    }
}