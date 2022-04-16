package org.karl.service.gateway.config;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.karl.sh.core.templates.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * feign调用错误处理
 *
 * @author KARL.ROSE
 * @date 2021/4/2 11:43
 */
@Component
public class AuthCheckDecoder implements ErrorDecoder {

    private static final Logger logger = LoggerFactory.getLogger(AuthCheckDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {
        BaseException exception = null;
        if (response.status() != HttpStatus.OK.value()) {
            if (null != response.body()) {
                String message;
                try {
                    message = Util.toString(response.body().asReader());
                    //异常信息
                    exception = new BaseException(message);
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return exception;
    }
}
