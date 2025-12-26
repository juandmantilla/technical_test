package co.com.bancolombia.model.exceptions;


import co.com.bancolombia.model.exceptions.error.types.ErrorType;

public class TechnicalException extends ApplicationException {

    public TechnicalException(String errorCode, String description) {
        super(errorCode, formatDescription(description), ErrorType.TECHNICAL);
    }

    private static String formatDescription(String description) {
        return "[ TECHNICAL FAILURE ] " + description;
    }
}
