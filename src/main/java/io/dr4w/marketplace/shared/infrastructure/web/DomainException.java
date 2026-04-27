package io.dr4w.marketplace.shared.infrastructure.web;

import org.springframework.http.HttpStatus;

public class DomainException extends RuntimeException {

    private final HttpStatus status;

    public DomainException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public static DomainException notFound(String message) {
        return new DomainException(message, HttpStatus.NOT_FOUND);
    }

    public static DomainException conflict(String message) {
        return new DomainException(message, HttpStatus.CONFLICT);
    }

    public static DomainException badRequest(String message) {
        return new DomainException(message, HttpStatus.BAD_REQUEST);
    }

    public static DomainException forbidden(String message) {
        return new DomainException(message, HttpStatus.FORBIDDEN);
    }

    public HttpStatus getStatus() {
        return status;
    }
}
