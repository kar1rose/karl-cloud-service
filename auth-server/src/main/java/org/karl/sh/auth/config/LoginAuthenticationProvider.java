package org.karl.sh.auth.config;

import org.karl.sh.core.constants.ExceptionCode;
import org.karl.sh.core.utils.PwdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @author KARL ROSE
 * @date 2020/9/17 18:25
 **/
@Component
//@FrameworkEndpoint
public class LoginAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsServiceImpl;

    @Autowired
    private void setJdbcUserDetailsService() {
        setUserDetailsService(customUserDetailsServiceImpl);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            throw new BadCredentialsException(ExceptionCode.USERNAME_NOT_FOUND);
        }
        String presentedPassword = authentication.getCredentials().toString();
        if (!PwdUtils.match(presentedPassword, userDetails.getPassword())) {
            throw new BadCredentialsException(ExceptionCode.PASSWORD_NOT_VALID);
        }
    }
}
