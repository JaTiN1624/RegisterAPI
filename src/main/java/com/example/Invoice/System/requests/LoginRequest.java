package com.example.Invoice.System.requests;

public class LoginRequest {


    private String email;
    private String password;

//    default Constructor
    public LoginRequest(){

    }

//    parameterized constructor
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

//    Getter and Setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
