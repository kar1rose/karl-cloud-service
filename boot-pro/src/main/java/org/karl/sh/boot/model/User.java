package org.karl.sh.boot.model;

import lombok.Data;

/**
 * @author KARL ROSE
 * @date 2020/7/20 16:08
 **/
public final class User {

    private String id;
    private String username;
    private String mail;

    public User() {
    }

    public User(String id, String username, String mail) {
        this.id = id;
        this.username = username;
        this.mail = mail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
