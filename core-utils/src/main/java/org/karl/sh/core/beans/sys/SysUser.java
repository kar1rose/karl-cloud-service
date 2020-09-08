package org.karl.sh.core.beans.sys;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author KARL ROSE
 * @date 2020/9/8 14:28
 **/
@Data
public class SysUser implements UserDetails {

    private Long userId;
    private String username;
    private String nickname;
    private String avatar;
    private String password;
    private String mail;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


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
