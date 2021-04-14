package org.karl.service.gateway.clients;

import org.karl.sh.core.templates.ApiResult;

import java.util.Map;

/**
 * Created by KARL_ROSE on 2021/4/11 11:24
 */
public interface IBaseClient {

    ApiResult<Object> post(String url, Map<String, String> params, Map<String, String> headers);


    ApiResult<Object> get(String url, Map<String, String> params, Map<String, String> headers);
}
