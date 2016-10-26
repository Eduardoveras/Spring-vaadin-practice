/**
 * Created by Djidjelly Siclait on 10/26/2016.
 */
package com.pucmm.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class User implements Serializable {
    // Atributes
    @Id
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    // Constructors
    public User(){

    }

    public User(String username, String firstName, String lastName, String email, String password){
        this.setUsername(username);
        this.setFirstName(firstName.toLowerCase());
        this.setLastName(lastName.toUpperCase());
        this.setEmail(email);
        this.setPassword(password);
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
