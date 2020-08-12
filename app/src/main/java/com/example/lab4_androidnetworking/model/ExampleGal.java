
package com.example.lab4_androidnetworking.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExampleGal {

    @SerializedName("photos")
    @Expose
    private PhotosGal photos;
    @SerializedName("stat")
    @Expose
    private String stat;

    public PhotosGal getPhotos() {
        return photos;
    }

    public void setPhotos(PhotosGal photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

}
