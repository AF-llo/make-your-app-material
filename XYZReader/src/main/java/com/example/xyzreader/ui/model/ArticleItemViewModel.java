package com.example.xyzreader.ui.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Html;

/**
 * Created by lars on 15.02.17.
 */

public class ArticleItemViewModel implements Parcelable {

    private long id;

    private String title = "";

    private String author = "";

    private long date = 0L;

    private String thumbImageUrl = "";

    private String body = "";

    private String info = "";

    private String photoUrl = "";

    private float imageRatio = 1;

    private ArticleItemViewModel() {
    }

    public ArticleItemViewModel(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null) {
            return;
        }
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        if (info == null) {
            return;
        }
        this.info = info;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (author == null) {
            return;
        }
        this.author = author;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getThumbImageUrl() {
        return thumbImageUrl;
    }

    public void setThumbImageUrl(String thumbImageUrl) {
        if (thumbImageUrl == null) {
            return;
        }
        this.thumbImageUrl = thumbImageUrl;
    }

    public String getBody() {
        return Html.fromHtml(body).toString();
    }

    public void setBody(String body) {
        if (body == null) {
            return;
        }
        this.body = body;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        if (photoUrl == null) {
            return;
        }
        this.photoUrl = photoUrl;
    }

    public float getImageRatio() {
        return imageRatio;
    }

    public void setImageRatio(float imageRatio) {
        if (imageRatio > 0)
        this.imageRatio = imageRatio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.author);
        dest.writeString(this.info);
        dest.writeLong(this.date);
        dest.writeString(this.thumbImageUrl);
        dest.writeString(this.body);
        dest.writeFloat(this.imageRatio);
    }

    protected ArticleItemViewModel(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.author = in.readString();
        this.info = in.readString();
        this.date = in.readLong();
        this.thumbImageUrl = in.readString();
        this.body = in.readString();
        this.imageRatio = in.readFloat();
    }

    public static final Parcelable.Creator<ArticleItemViewModel> CREATOR = new Parcelable.Creator<ArticleItemViewModel>() {
        @Override
        public ArticleItemViewModel createFromParcel(Parcel source) {
            return new ArticleItemViewModel(source);
        }

        @Override
        public ArticleItemViewModel[] newArray(int size) {
            return new ArticleItemViewModel[size];
        }
    };
}
