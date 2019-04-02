package com.example.easystartup.Investor.model;

public class MyModel {

    private String Description;
    private String Subtitle;
    private String Title;
    private String Email;
    private String Name;
    private String Nationality;
    private String PhoneNo;


    private String Image;

    public MyModel() {
    }

    public MyModel(String description, String subtitle, String title, String email, String name, String nationality, String phoneNo, String image) {
        Description = description;
        Subtitle = subtitle;
        Title = title;
        Email = email;
        Name = name;
        Nationality = nationality;
        PhoneNo = phoneNo;
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSubtitle() {
        return Subtitle;
    }

    public void setSubtitle(String subtitle) {
        Subtitle = subtitle;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }
}
