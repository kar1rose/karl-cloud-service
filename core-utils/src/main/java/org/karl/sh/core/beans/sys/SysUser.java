package org.karl.sh.core.beans.sys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * @author KARL ROSE
 * @date 2020/9/8 14:28
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUser implements UserDetails {

    private static final long serialVersionUID = -1L;

    private Long userId;
    private String username;
    private String nickname;
    private String avatar;
    private String password;
    private String mail;

    private List<SysAuth> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
