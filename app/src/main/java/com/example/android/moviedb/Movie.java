package com.example.android.moviedb;

public class Movie {
    private String mTitle;
    private String mUrlImage;
    private float mRate;
    private String mDescription;
    private String mDate;

    public Movie(String mTitle, String mUrlImage, float mRate,String mDescription,String mDate) {
        this.mTitle = mTitle;
        this.mUrlImage = mUrlImage;
        this.mRate = mRate;
        this.mDescription=mDescription;
        this.mDate = mDate;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmUrlImage() {
        return mUrlImage;
    }

    public float getmRate() {
        return mRate;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmDate() {
        return mDate;
    }
}
