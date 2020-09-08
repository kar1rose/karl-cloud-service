package org.karl.sh.provider.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.karl.sh.core.templates.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @author KARL ROSE
 * @date 2020/9/8 17:06
 **/
@RequestMapping("/")
@RestController
@Slf4j
public class IndexController {

    @Autowired
    private RestTemplate restTemplate;

    private static final int HTTP_CONN_TIME_OUT = 3000;

    /**
     * 请求配置
     **/
    private static final RequestConfig REQUEST_CONFIG = RequestConfig.custom().
            setConnectionRequestTimeout(HTTP_CONN_TIME_OUT)
            .setConnectTimeout(HTTP_CONN_TIME_OUT)
            .setSocketTimeout(HTTP_CONN_TIME_OUT)
            .build();

    @PostMapping("login")
    public ResponseEntity<String> login() throws Exception {
        URI uri = new URIBuilder("http://127.0.0.1:8888/oauth/authorize")
                .addParameter("response_type", "code")
                .addParameter("client_id", "client-A")
                .addParameter("redirect_uri", "http://127.0.0.1:8000/code").build();

        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 定义请求的参数
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setConfig(REQUEST_CONFIG);
        //response 对象
        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY) {
                String setCookie = response.getFirstHeader("Set-cookie").getValue();
                String cookie = setCookie.substring(0, setCookie.indexOf(";"));

                MultiValueMap<String, String> loginParams = new LinkedMultiValueMap<>();
                loginParams.add("username", "karl");
                loginParams.add("password", "karl");
                HttpHeaders loginHeader = new HttpHeaders();
                loginHeader.set("Cookie", cookie);
                HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(loginParams, loginHeader);
                String loginUrl = "http://127.0.0.1:8000/login";
                ResponseEntity<String> result = restTemplate.postForEntity(loginUrl, entity, String.class);
                log.info("----登陆请求结果:{}----", result);
                return result;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(e.getMessage());
        } finally {
            httpclient.close();
        }
        return null;
    }

    @GetMapping("code")
    public ResponseEntity<String> code(@RequestParam("/code") String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("code", code);
        params.add("client_id", "client-A");
        params.add("client_secret", "karl");
        params.add("redirect_uri", "www.baidu.com");
        params.add("scope", "cuckoo-service");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "multipart/form-data");
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        ResponseEntity<String> result = restTemplate.postForEntity("http://127.0.0.1:8888/oauth/token", entity, String.class);
        log.info("----获取token结果:{}----", result);
        return result;

    }

}
