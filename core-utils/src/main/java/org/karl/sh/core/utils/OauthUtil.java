package org.karl.sh.core.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.karl.sh.core.templates.BaseException;
import org.karl.sh.core.templates.JwtDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;


/**
 * @author KARL.ROSE
 * @date 2020/5/18 1:42 下午
 **/
public class OauthUtil {

    public static final String GRANT_TYPE_CODE = "authorization_code";
    public static final String GRANT_TYPE_PASSWORD = "password";
    public static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";


    /**
     * 登陆获取token
     *
     * @author KARL.ROSE
     * @date 2019/10/18 3:28 下午
     **/
    public static JwtDto getToken(String tokenUrl, String oauthCode, String username, String password) throws BaseException, URISyntaxException {
        String result;
        URI uri = new URIBuilder(tokenUrl)
                .addParameter("grant_type", GRANT_TYPE_PASSWORD)
                .addParameter("username", username)
                .addParameter("password", password)
                .build();
        HttpPost post = new HttpPost(uri);
        post.setConfig(HttpUtils.REQUEST_CONFIG);
        post.addHeader(new BasicHeader(HttpHeaders.AUTHORIZATION, "Basic ".concat(Base64Encoder.encode(oauthCode.getBytes(StandardCharsets.UTF_8)))));
        post.addHeader(new BasicHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse httpResponse = client.execute(post)) {
            if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
            } else {
                throw new BaseException("request failed");
            }
        } catch (Exception e) {
            throw new BaseException(e.getMessage());
        }
        return JSON.parseObject(result, JwtDto.class);
    }

    public static JwtDto getTokenByAuthCode(String tokenUrl, String oauthCode, String redirectUri, String code) throws BaseException, URISyntaxException {
        String result;
        HttpPost post = new HttpPost(new URIBuilder(tokenUrl)
                .addParameter("grant_type", GRANT_TYPE_CODE)
                .addParameter("redirect_uri", redirectUri)
                .addParameter("code", code)
                .build());
        post.setConfig(HttpUtils.REQUEST_CONFIG);
        post.addHeader(new BasicHeader(HttpHeaders.AUTHORIZATION, "Basic ".concat(Base64Encoder.encode(oauthCode.getBytes(StandardCharsets.UTF_8)))));
        post.addHeader(new BasicHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse httpResponse = client.execute(post)) {
            if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
            } else {
                throw new BaseException("request failed");
            }
        } catch (Exception e) {
            throw new BaseException(e.getMessage());
        }
        return JSON.parseObject(result, JwtDto.class);
    }

    /**
     * token刷新
     *
     * @author KARL.ROSE
     * @date 2019/10/18 3:28 下午
     **/
    public static JwtDto refreshToken(String tokenUrl, String token, String clientId, String clientSecret) throws BaseException, URISyntaxException, IOException {
        String result = null;
        URI uri = new URIBuilder(tokenUrl)
                .addParameter("refresh_token", token)
                .addParameter("grant_type", GRANT_TYPE_REFRESH_TOKEN)
                .addParameter("client_id", clientId)
                .addParameter("client_secret", clientSecret)
                .build();
        HttpPost post = new HttpPost(uri);
        post.setConfig(HttpUtils.REQUEST_CONFIG);
        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse httpResponse = client.execute(post)) {
            if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
            } else {
                throw new BaseException("request failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSON.parseObject(result, JwtDto.class);
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        //获取token
        JwtDto token = getToken("http://172.23.41.184:8888/oauth/token", "client-A:karl", "karl", "karl");
        System.out.println(JsonUtils.convert(token));

        //刷新token
//        JwtDto refreshToken = refreshToken("http://172.23.41.184:8888/oauth/token", "60d9d372-9b9f-4788-abb5-be2aba12ff88", "client-A", "karl");
//        System.out.println(JsonUtils.convert(refreshToken));
    }
}
