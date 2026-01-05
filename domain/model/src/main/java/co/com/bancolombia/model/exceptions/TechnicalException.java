package co.com.bancolombia.model.exceptions;


public class TechnicalException extends ApplicationException {

    public TechnicalException(String errorCode, String description) {
        super(errorCode, formatDescription(description));
    }

    private static String formatDescription(String description) {
        return "[ TECHNICAL FAILURE ] " + description;
    }
}
