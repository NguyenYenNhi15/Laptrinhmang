/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package main.java.baitap.jsp;

import java.io.Serializable;

/**
 *
 * @author Minh ngo
 */
public class User implements Serializable{
    private String userName;
    private String password;
    public User(){ }
    public User(String username, String password){
        this.userName = username;
        this.password = password;
    }
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
}

