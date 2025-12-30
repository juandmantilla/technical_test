package co.com.bancolombia.model.exceptions;

import co.com.bancolombia.model.exceptions.error.types.ErrorType;
import lombok.Getter;

@Getter
public abstract class ApplicationException extends RuntimeException {

    private final String errorCode;
    private final String description;
    private final ErrorType errorType;

    protected ApplicationException(String errorCode, String description, ErrorType errorType) {
        super(description);
        this.errorCode = errorCode;
        this.description = description;
        this.errorType = errorType;
    }
}
