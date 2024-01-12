package com.example.shivamscart.Models;

public class Users {
   private String Email,Number,Name,Password,Dob,gender,seller;

    public Users() {

    }

    public Users(String email, String number, String name, String password, String dob, String gender, String seller) {
        Email = email;
        Number = number;
        Name = name;
        Password = password;
        Dob = dob;
        this.gender = gender;
        this.seller = seller;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
