package org.karl.sh.boot.controller;

import org.karl.sh.boot.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author KARL ROSE
 * @date 2020/7/20 15:51
 **/
@RestController
@RequestMapping("/")
public class IndexController {

    private final HashMap<String, User> map = new HashMap<>();


    @GetMapping("user/{id}")
    public ResponseEntity<User> user(@PathVariable String id) {
        printlnThread();
        return new ResponseEntity<>(map.get(id), HttpStatus.OK);
    }

    @GetMapping("user")
    public Flux<User> getAll() {
        printlnThread();
        //使用lambda表达式
        return Flux.fromStream(map.values().stream());
    }

    @PostMapping("user")
    public ResponseEntity<User> save(@RequestBody User user) {
        printlnThread();
        String id = UUID.randomUUID().toString();
        user.setId(id);
        map.put(id, user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    private void printlnThread() {
        System.out.println("Controller[" + Thread.currentThread().getName() + "]");
    }
}
