package org.karl.sh.core.strategy;

import org.karl.sh.core.utils.JsonUtils;
import org.springframework.security.oauth2.provider.token.store.redis.StandardStringSerializationStrategy;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * oauth2 tokenStore redis序列化策略
 *
 * @author KARL ROSE
 * @date 2020/9/9 16:14
 **/
public class Oauth2JsonSerializationStrategy extends StandardStringSerializationStrategy {

    @Override
    protected <T> T deserializeInternal(byte[] bytes, Class<T> clazz) {
        return JsonUtils.parse(new String(bytes, StandardCharsets.UTF_8), clazz);
    }

    @Override
    protected byte[] serializeInternal(Object object) {
        return Objects.requireNonNull(JsonUtils.convert(object)).getBytes();
    }


}
