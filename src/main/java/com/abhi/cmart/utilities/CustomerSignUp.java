package com.abhi.cmart.utilities;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CustomerSignUp {

    private int id;

    @NotBlank(message = "Field is required")
    private String firstName;

    @NotBlank(message = "Field is required")
    private String lastName;

    @NotBlank(message = "Field is required")
    @Email(message = "Not a valid email")
    private String email;

    @Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,10}$",message="Should contain minimum 8 and maximum 10 characters, at least 1 uppercase letter, 1 lowercase letter, 1 number and 1 special character")
    private String password;

    public CustomerSignUp() {
    }

    public CustomerSignUp(int id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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