package kr.co.datastreams.llmetabe.api.extraction.exception;

import kr.co.datastreams.llmetabe.global.advice.ErrorCode;
import kr.co.datastreams.llmetabe.global.advice.ErrorMessage;
import kr.co.datastreams.llmetabe.global.exception.GlobalException;

public class FileInputStreamException extends GlobalException {
    public FileInputStreamException() {
        super(ErrorMessage.FILE_IO_ERROR.getMessage());
    }

    @Override
    public ErrorCode code() {
        return ErrorCode.F01;
    }
}
