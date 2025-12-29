package co.com.bancolombia.api.config;


import co.com.bancolombia.api.dtos.outs.ErrorDTO;
import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.exceptions.TechnicalException;
import co.com.bancolombia.model.messages.ResponseMessages;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


public abstract class ApplicationExceptionHandler {

    protected abstract Logger getLogger();

    public Mono<ServerResponse> handleException(Throwable th) {

        if (th instanceof TechnicalException ex) {
            getLogger().error("Technical Error: {}", ex.getDescription());
            var error = ErrorDTO.builder()
                    .code(ex.getErrorCode())
                    .description(ex.getDescription())
                    .build();

            return ServerResponse.status(Integer.parseInt(ex.getErrorCode())).bodyValue(error);
        }


        if (th instanceof BusinessException ex) {
            getLogger().error("Business Error: {}", ex.getDescription());
            var error = ErrorDTO.builder()
                    .code(ex.getErrorCode())
                    .description(ex.getDescription())
                    .build();

            return ServerResponse.status(Integer.parseInt(ex.getErrorCode())).bodyValue(error);
        }

        getLogger().error("Generic Error: {}", th.getMessage());
        var error = ErrorDTO
                .builder()
                .code(ResponseMessages.INTERNAL_SERVER_ERROR.getStatusCode())
                .description(ResponseMessages.INTERNAL_SERVER_ERROR.getDescription())
                .build();

        return ServerResponse
                .status(500)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(error);
    }
}
