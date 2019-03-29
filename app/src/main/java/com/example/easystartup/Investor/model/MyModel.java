package com.example.easystartup.Investor.model;

public class MyModel {

    private String Subtitle;
    private String Title;


    String image;

    public MyModel() {
    }

    public MyModel(String subtitle, String title, String image) {
        Subtitle = subtitle;
        Title = title;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getSubtitle() {
        return Subtitle;
    }

    public void setSubtitle(String Subtitle) {
        this.Subtitle = Subtitle;
    }
}
