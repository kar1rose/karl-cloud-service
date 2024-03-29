package org.karl.sh.core.beans.sys;

import java.io.Serializable;
import java.util.List;

/**
 * token鉴权返回bean
 *
 * @author KARL.ROSE
 * @date 2021/4/2 10:38
 */
public class AuthTokenCheckDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String error;

    private String error_description;

    private Long exp;

    private String user_name;

    private List<String> authorities;

    private String client_id;

    private List<String> scope;

    public Long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public List<String> getScope() {
        return scope;
    }

    public void setScope(List<String> scope) {
        this.scope = scope;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }
}
