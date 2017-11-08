/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ats.dtos;

/**
 *
 * @author Chi Hieu
 */
public class LoginDTO {

    private String username;
    private String password;
    private String role;
    private String fullname;
    private String localhost;

    public LoginDTO() {
    }

    public LoginDTO(String username, String role, String fullname, String localhost) {
        this.username = username;
        this.role = role;
        this.fullname = fullname;
        this.localhost = localhost;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getLocalhost() {
        return localhost;
    }

    public void setLocalhost(String localhost) {
        this.localhost = localhost;
    }

}
