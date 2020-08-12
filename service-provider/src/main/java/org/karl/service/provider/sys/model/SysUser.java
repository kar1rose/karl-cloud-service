package org.karl.service.provider.sys.model;

/**
 * @author KARL ROSE
 * @date 2020/8/11 16:07
 **/
public class SysUser {

    private Long id;
    private String username;
    private String password;

    public SysUser() {
    }

    public SysUser(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
