package kr.co.datastreams.llmetabe.api.extraction.exception;

import kr.co.datastreams.llmetabe.global.advice.ErrorCode;
import kr.co.datastreams.llmetabe.global.advice.ErrorMessage;
import kr.co.datastreams.llmetabe.global.exception.GlobalException;

public class FlaskHttpNoResponseException extends GlobalException {
    public FlaskHttpNoResponseException() {
        super(ErrorMessage.HTTP_NO_RESPONSE.getMessage());
    }

    @Override
    public ErrorCode code() {
        return ErrorCode.R04;
    }
}
