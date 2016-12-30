package com.praveen.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {

    public User() {}

    @NotNull
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column
    private int userType;

    @Column
    private String verificationCode;

    @NotNull
    @Column(name = "verified")
    private boolean verified;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Id
    @GeneratedValue()
    @Column(name = "id")
    private Integer id;

    public String getVerificationCode(){
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode){
        this.verificationCode=verificationCode;
    }

    public void setUserType(int i){
        userType=i;
    }

    public int getUserType(){
        return userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String user) {
        this.email = user;
    }

    public boolean getVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
