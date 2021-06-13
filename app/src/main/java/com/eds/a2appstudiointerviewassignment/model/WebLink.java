package com.eds.a2appstudiointerviewassignment.model;

import android.graphics.Bitmap;

import java.util.Objects;

public class WebLink {

    private String webLink;
    private String title;
    public String imageUrl;

    public WebLink(String webLink, String title, String imageUrl) {
        this.webLink = webLink;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebLink webLink1 = (WebLink) o;
        return Objects.equals(webLink, webLink1.webLink) &&
                Objects.equals(title, webLink1.title) &&
                Objects.equals(imageUrl, webLink1.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(webLink, title, imageUrl);
    }
}
