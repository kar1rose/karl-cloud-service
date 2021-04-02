package org.karl.sh.warehouse.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.karl.sh.core.beans.sys.SysAuth;
import org.karl.sh.core.beans.sys.SysUser;

import java.util.List;

/**
 * @author KARL ROSE
 * @date 2020/9/15 16:12
 **/
@Data
@NoArgsConstructor
public class OauthAuthenticationVo {

    private List<SysAuth> authorities;

    private SysUser principal;

}
