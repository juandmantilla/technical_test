package co.com.bancolombia.model.exceptions;


public class BusinessException extends ApplicationException {

    public BusinessException(String errorCode, String description) {
        super(errorCode, formatDescription(description));
    }

    private static String formatDescription(String description) {
        return "[ BUSINESS FAILURE ] " + description;
    }
}
