package kr.co.datastreams.llmetabe.api.auth.exception;

import kr.co.datastreams.llmetabe.global.advice.ErrorCode;
import kr.co.datastreams.llmetabe.global.exception.GlobalException;

public class PasswordMismatchException extends GlobalException {

    public PasswordMismatchException(String message) {
        super(message);
    }

    @Override
    public ErrorCode code() {
        return ErrorCode.P01;
    }
}
