package com.example.datademo.data;

import java.io.Serializable;
import java.util.Date;

public class Person implements Serializable {
    public String username;
    public String password;
    public String firstName;
    public String lastName;
    public String phoneNumber;
    public String emailAddress;
    public String homeAddress;
    public String networkType;
    public String nrcNumber;
    public String licenseNumber;
    public Date licenseExpiryDate;

    public final String profile = "profile.jpeg";
    public final String nrcFront = "NRC-Front.jpeg";
    public final String nrcBack = "NRC-Front.jpeg";
    public final String licenseFront = "License-Front.jpeg";
    public final String licenseBack = "License-Bock.jpeg";

    public Person(){}

    public Person(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
