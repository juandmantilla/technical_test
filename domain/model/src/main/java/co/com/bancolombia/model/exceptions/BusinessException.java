package co.com.bancolombia.model.exceptions;


import co.com.bancolombia.model.exceptions.error.types.ErrorType;

public class BusinessException extends ApplicationException {

    public BusinessException(String errorCode, String description) {
        super(errorCode, formatDescription(description), ErrorType.BUSINESS);
    }

    private static String formatDescription(String description) {
        return "[ BUSINESS FAILURE ] " + description;
    }
}
