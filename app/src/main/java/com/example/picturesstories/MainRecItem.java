package com.example.picturesstories;

public class MainRecItem {

    private int mImageResource;
    private String mText1;

    public MainRecItem() {
    }

    public MainRecItem(int mImageResource, String mText1) {
        this.mImageResource = mImageResource;
        this.mText1 = mText1;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public void setmImageResource(int mImageResource) {
        this.mImageResource = mImageResource;
    }

    public String getmText1() {
        return mText1;
    }

    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }
}
