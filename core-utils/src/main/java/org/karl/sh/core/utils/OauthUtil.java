package org.karl.sh.core.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.karl.sh.core.templates.BaseException;
import org.karl.sh.core.templates.JwtDto;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


/**
 * @author KARL.ROSE
 * @date 2020/5/18 1:42 下午
 **/
public class OauthUtil {

    /**
     * 登陆获取token
     *
     * @author KARL.ROSE
     * @date 2019/10/18 3:28 下午
     **/
    public static JwtDto getToken(String tokenUrl, String oauthCode, String username, String password) throws BaseException {
        Map<String, String> params = new HashMap<>(16);
        Map<String, String> headers = new HashMap<>(16);
        headers.put("Authorization", "Basic ".concat(Base64Encoder.encode(oauthCode.getBytes(StandardCharsets.UTF_8))));
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        params.put("grant_type", "password");
        params.put("username", username);
        params.put("password", password);
        String result = HttpUtils.sendPostByForm(tokenUrl, headers, params);
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
        CloseableHttpClient client = HttpClients.createDefault();
        URI uri = new URIBuilder(tokenUrl)
                .addParameter("refresh_token", token)
                .addParameter("grant_type", "refresh_token")
                .addParameter("client_id", clientId)
                .addParameter("client_secret", clientSecret)
                .build();
        HttpPost post = new HttpPost(uri);
        post.setConfig(HttpUtils.REQUEST_CONFIG);
        try (CloseableHttpResponse httpResponse = client.execute(post)) {

            result = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.close();
            }
        }
        return JSON.parseObject(result, JwtDto.class);
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        //获取token
        /*JwtDto dto = getToken("http://172.23.41.184:8888/oauth/token", "client-A:karl", "karl", "karl");
        System.out.println(JsonUtils.convert(dto));*/

        //刷新token
        JwtDto dto = refreshToken("http://172.23.41.184:8888/oauth/token", "60d9d372-9b9f-4788-abb5-be2aba12ff88", "client-A", "karl");
        System.out.println(JsonUtils.convert(dto));
    }
}
