package edu.volstate.flickrliker.models;

import android.os.Parcel;
import android.os.Parcelable;

public class FlickrPicture implements Parcelable {
    private String id;
    private String secret;
    private String server;
    private String title;
    private boolean favorite;

    public FlickrPicture(String id, String secret, String server, String title, boolean favorite) {
        this.id = id;
        this.secret = secret;
        this.server = server;
        this.title = title;
        this.favorite = favorite;
    }

    protected FlickrPicture(Parcel in) {
        id = in.readString();
        secret = in.readString();
        server = in.readString();
        title = in.readString();
        favorite = in.readByte() != 0;
    }

    public static final Creator<FlickrPicture> CREATOR = new Creator<FlickrPicture>() {
        @Override
        public FlickrPicture createFromParcel(Parcel in) {
            return new FlickrPicture(in);
        }

        @Override
        public FlickrPicture[] newArray(int size) {
            return new FlickrPicture[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(secret);
        parcel.writeString(server);
        parcel.writeString(title);
        parcel.writeByte((byte) (favorite ? 1 : 0));
    }
}
