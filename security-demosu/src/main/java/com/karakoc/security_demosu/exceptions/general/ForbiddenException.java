package com.karakoc.security_demosu.exceptions.general;

import com.karakoc.security_demosu.exceptions.RestException;
import lombok.Getter;
import org.springframework.http.HttpStatus;


public class ForbiddenException extends RestException {
    @Getter
    private final HttpStatus httpStatus;

    public ForbiddenException(String msg) {
        super(msg);
        this.httpStatus = HttpStatus.FORBIDDEN;
    }

}

