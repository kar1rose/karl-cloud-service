package org.karl.sh.service.mail.controller;
/**
 * Created by KARL_ROSE on 2021/4/6 20:39
 */

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
    public Mono<String> hello (@PathVariable(name = "username") String username) {
        return Mono.just("hello:" + username);
    }

    @GetMapping("/flux")
    public Flux<String> hiFlux(@RequestParam(name = "name") String name) {
        return Flux.just("hiFlux:" + name);
    }

    @PostMapping("/flux/{username}")
    public Flux<String> helloFlux (@PathVariable(name = "username") String username) {
        return Flux.just("helloFlux:" + username);
    }

}
