package org.karl.sh.service.mail.controller;
/**
 * Created by KARL_ROSE on 2021/4/6 20:39
 */

import org.karl.sh.core.templates.ApiResult;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author ROSE
 * @date 2021/4/6 20:39
 **/
@RestController
@RequestMapping("/mail")
public class MailController {

    @GetMapping
    public Mono<String> hi(@RequestParam(name = "name") String name) {
        return Mono.just("hello:" + name);
    }

    @PostMapping("/{username}")
    public Mono<ApiResult<String>> hello(@PathVariable(name = "username") String username) {
        return Mono.just(ApiResult.success("hello:" + username));
    }

    @GetMapping("/flux")
    public Flux<String> hiFlux(@RequestParam(name = "name") String name) {
        return Flux.just("hiFlux:" + name);
    }

    @PostMapping("/flux/{name}")
    public Flux<ApiResult<String>> helloFlux(@PathVariable(name = "name") String name,
                                             @RequestParam(name = "username") String username) {
        return Flux.just(ApiResult.success("helloFlux:" + name + username));
    }

    @GetMapping("/get")
    public ApiResult<String> get(@RequestHeader(name = "userId") String userId,
                                 @RequestParam(name = "username") String username) {
        String msg = "hello :" + userId + ":" + username;
        return ApiResult.success(msg);
    }

}
