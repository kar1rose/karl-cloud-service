package org.karl.service.gateway.clients;

import org.karl.sh.core.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import javax.xml.ws.Response;

/**
 * @author ROSE
 * @date 2021/4/6 20:28
 **/
public abstract class AbstractBaseClient {

    @Autowired
    private WebClient webClient;

    private Object data;

    /*protected Object data(String url) {
        Mono<Object> mono = webClient.post().uri(url).retrieve().bodyToMono(Object.class);

    }*/

    public static void main(String[] args) {
        Mono<ClientResponse> mono = WebClient.create("http://127.0.0.1:8080/mail").get().uri(uriBuilder -> uriBuilder
//                .scheme("http")
//                .host("127.0.0.1").port(8080).path("mail")
                .queryParam("name", "rose")
                .build()).exchange();
        /*mono.flatMap(clientResponse -> {
            if (clientResponse.statusCode() != HttpStatus.OK) {
                System.out.println("error......");
            }
            return clientResponse.bodyToMono(String.class);
        });*/
        ClientResponse response = mono.block();
        assert response != null;
        Mono<ResponseEntity<String>> result = response.toEntity(String.class);
        ResponseEntity<String> s = result.block();
        assert s != null;
        System.out.println(s.getBody());
    }

}
