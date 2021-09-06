package com.example.picturesstories;

public class AddImage {

    String id;
    private String title;
    private String image;
    private String titleGroup;

    public AddImage() {
    }

    public AddImage(String id, String title, String image , String titleGroup) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.titleGroup = titleGroup;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitleGroup() {
        return titleGroup;
    }

    public void setTitleGroup(String titleGroup) {
        this.titleGroup = titleGroup;
    }
}
