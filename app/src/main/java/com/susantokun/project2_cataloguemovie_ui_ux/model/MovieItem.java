package com.susantokun.project2_cataloguemovie_ui_ux.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;

public class MovieItem implements Parcelable {
    private int id;
    private String mov_title;
    private String mov_overview;
    private String mov_vote_average;
    private String mov_releasedate;
    private String mov_poster;
    private String mov_rate_count;
    private String mov_rate;
    private String mov_poster_backdrop;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMov_title() {
        return mov_title;
    }

    public void setMov_title(String mov_title) {
        this.mov_title = mov_title;
    }

    public String getMov_overview() {
        return mov_overview;
    }

    public void setMov_overview(String mov_overview) {
        this.mov_overview = mov_overview;
    }

    public String getMov_vote_average() {
        return mov_vote_average;
    }

    public void setMov_vote_average(String mov_vote_average) {
        this.mov_vote_average = mov_vote_average;
    }

    public String getMov_releasedate() {
        return mov_releasedate;
    }

    public void setMov_releasedate(String mov_releasedate) {
        this.mov_releasedate = mov_releasedate;
    }

    public String getMov_poster() {
        return mov_poster;
    }

    public void setMov_poster(String mov_poster) {
        this.mov_poster = mov_poster;
    }

    public String getMov_rate_count() {
        return mov_rate_count;
    }

    public void setMov_rate_count(String mov_rate_count) {
        this.mov_rate_count = mov_rate_count;
    }

    public String getMov_rate() {
        return mov_rate;
    }

    public void setMov_rate(String mov_rate) {
        this.mov_rate = mov_rate;
    }

    public String getMov_poster_backdrop() {
        return mov_poster_backdrop;
    }

    public void setMov_poster_backdrop(String mov_poster_backdrop) {
        this.mov_poster_backdrop = mov_poster_backdrop;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.mov_title);
        dest.writeString(this.mov_overview);
        dest.writeString(this.mov_releasedate);
        dest.writeString(this.mov_poster);
        dest.writeString(this.mov_rate_count);
        dest.writeString(this.mov_rate);
        dest.writeString(this.mov_poster_backdrop);
    }

    protected MovieItem(Parcel in) {
        this.id = in.readInt();
        this.mov_title = in.readString();
        this.mov_overview = in.readString();
        this.mov_releasedate = in.readString();
        this.mov_poster = in.readString();
        this.mov_rate_count = in.readString();
        this.mov_rate = in.readString();
        this.mov_poster_backdrop = in.readString();
    }

    public static final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel source) {
            return new MovieItem(source);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };
}
