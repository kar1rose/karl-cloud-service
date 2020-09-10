package org.karl.sh.core.beans.sys;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author KARL ROSE
 * @date 2020/9/9 14:53
 **/
@Data
@AllArgsConstructor
public class SysAuth implements GrantedAuthority {

    private static final long serialVersionUID = -1L;

    private String authId;

    private String authCode;

    private String authority;

}
