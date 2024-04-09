package kr.co.datastreams.llmetabe.global.exception;

import kr.co.datastreams.llmetabe.global.advice.ErrorCode;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class GlobalException extends RuntimeException {
    private final Map<String, String> validation = new HashMap<>();

    public GlobalException(String message) {
        super(message);
    }

    public abstract ErrorCode code();
}