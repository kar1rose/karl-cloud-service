package org.karl.sh.core.templates;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * @author KARL.ROSE
 * @date 2020/5/18 12:37 下午
 **/
@Data
public class JwtDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String access_token;

    private String token_type;

    private String refresh_token;

    private int expires_in;

    private String scope;

    private String jti;

}
