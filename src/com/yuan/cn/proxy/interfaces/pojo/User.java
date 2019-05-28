package com.yuan.cn.proxy.interfaces.pojo;

import com.yuan.cn.proxy.interfaces.Column;

import java.util.List;

/**
 * @author Crazy
 * @date 2018/12/1
 */
public class User {
    @Column(name="id")
    private Integer id;
    @Column(name="t_username")
    private String username;
    private String password;

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
