package co.com.bancolombia.api.helpers;


import co.com.bancolombia.api.dtos.outs.ResponseDTO;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class ResponseBuilder {

    private ResponseBuilder(){}

    public static <T> Mono<ServerResponse> buildSuccessResponse(String statusCode, String description, T body) {
        var build = ResponseDTO.builder()
                .code(statusCode)
                .description(description)
                .data(body)
                .build();
        return ServerResponse.ok().bodyValue(build);
    }

}
