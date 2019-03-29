package com.example.easystartup.Investor.model;

public class MyModel {

    private String Subtitle;
    private String Title;


    int images;

    public MyModel() {
    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }

    public MyModel(String Subtitle, String Title, int images) {
        this.Subtitle = Subtitle;
        this.Title = Title;
        this.images = images;

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
