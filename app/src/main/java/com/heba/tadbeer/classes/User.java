package com.heba.tadbeer.classes;

/**
 * Created by root on 3/5/16.
 */
public class User {
    private int Id;
    private String Firstname;
    private String Lastname;
    private String Email;

    public User(int id, String firstname, String lastname, String email) {
        Id = id;
        Firstname = firstname;
        Lastname = lastname;
        Email = email;
    }

    public int getId() {
        return Id;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
