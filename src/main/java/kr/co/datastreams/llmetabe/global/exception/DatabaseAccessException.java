package kr.co.datastreams.llmetabe.global.exception;

import kr.co.datastreams.llmetabe.global.advice.ErrorCode;
import kr.co.datastreams.llmetabe.global.advice.ErrorMessage;

public class DatabaseAccessException extends GlobalException {

    public DatabaseAccessException() {
        super(ErrorMessage.DATABASE_ERROR.getMessage());
    }

    @Override
    public ErrorCode code() {
        return ErrorCode.D02;
    }
}