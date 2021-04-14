package org.karl.service.gateway.clients;

import org.karl.sh.core.templates.ApiResult;
import org.karl.sh.core.utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ROSE
 * @date 2021/4/11 11:28
 **/
public class AuthClientImpl extends AbstractBaseClient {


    public static void main(String[] args) {
        IBaseClient client = new AuthClientImpl();

        Map<String, String> heads = new HashMap<>(16);
        heads.put("userId", "20210407");
        Map<String, String> params = new HashMap<>(16);
        params.put("username", "rose");
        ApiResult<Object> s = client.get("http://127.0.0.1:8080/mail/flux/rose", params, heads);
        if (s.getData() instanceof String) {
            System.out.println(JsonUtils.convert(s));
        } else {
            System.out.println("不是字符串");
        }
    }

}
