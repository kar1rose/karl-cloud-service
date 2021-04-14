package org.karl.service.gateway.clients;

import org.karl.sh.core.templates.ApiResult;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author ROSE
 * @date 2021/4/6 20:28
 **/
public abstract class AbstractBaseClient implements IBaseClient {


    @Override
    public ApiResult<Object> get(String url, Map<String, String> params, Map<String, String> headers) {
        Mono<ApiResult<Object>> mono = WebClient.create(url).get()
                .uri(uriBuilder -> {
                    params.forEach(uriBuilder::queryParam);
                    return uriBuilder.build();
                })
                .headers(head -> headers.forEach(head::add))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResult<Object>>() {
                });
        return mono.block();
    }

    @Override
    public ApiResult<Object> post(String url, Map<String, String> params, Map<String, String> headers) {
        Mono<ClientResponse> mono = WebClient.create(url).post()
                .uri(uriBuilder -> {
                    params.forEach(uriBuilder::queryParam);
                    return uriBuilder.build();
                })
                .headers(head -> headers.forEach(head::add))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange();
        ClientResponse response = mono.block();
        assert response != null;
        Mono<ResponseEntity<ApiResult<Object>>> result = response.toEntity(new ParameterizedTypeReference<ApiResult<Object>>() {
        });
        ResponseEntity<ApiResult<Object>> s = result.block();
        assert s != null;
        return s.getBody();
    }


}
