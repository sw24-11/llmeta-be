package kr.co.datastreams.llmetabe.global.utils;


import kr.co.datastreams.llmetabe.global.utils.paging.Paging;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Getter
@Builder
public class Response<T> {

    private int status;

    private String message;

    private T data;

    public static Response<?> ok(String message) {

        return Response.builder()
                .status(OK.value())
                .message(message)
//                .data(Collections.EMPTY_LIST)
                .build();
    }

    public static <T> Response<?> ok(T data, String message) {

        return Response.builder()
                .status(OK.value())
                .message(message)
                .data(data)
                .build();
    }

    public static <T> Response<?> ok(T data) {

        return Response.builder()
                .status(OK.value())
                .data(data)
                .build();
    }

    public static Response<?> list(Page<?> data, String message) {

        return Response.builder()
                .status(OK.value())
                .message(message)
                .data(Paging.of(data))
                .build();
    }

    public static Response<?> badRequest(String errorMessage) {
        return Response.builder()
                .status(BAD_REQUEST.value())
                .message(errorMessage)
                .build();
    }

    public static Response<?> unauthorized(String errorMessage) {
        return Response.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(errorMessage)
                .build();
    }
}