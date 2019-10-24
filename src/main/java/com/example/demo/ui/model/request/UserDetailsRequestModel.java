package com.example.demo.ui.model.request;

public class UserDetailsRequestModel {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    private void setLastName(String lastName) {
        this.lastName = lastName;
    }
    private void setEmail(String email) {
        this.email = email;
    }
    private void setPassword(String password) {
        this.password = password;
    }
}
