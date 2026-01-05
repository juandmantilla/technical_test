package co.com.bancolombia.model.exceptions;

import lombok.Getter;

@Getter
public abstract class ApplicationException extends RuntimeException {

    private final String errorCode;
    private final String description;

    protected ApplicationException(String errorCode, String description) {
        super(description);
        this.errorCode = errorCode;
        this.description = description;
    }
}
