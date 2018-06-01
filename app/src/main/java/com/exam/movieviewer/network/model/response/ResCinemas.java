package com.exam.movieviewer.network.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Eunice Galang on 5/30/2018.
 */

public class ResCinemas {
    public String parent;
    @SerializedName("cinemas")
    public List<ResCinemasChild> cinemasChildren;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public List<ResCinemasChild> getCinemasChildren() {
        return cinemasChildren;
    }

    public void setCinemasChildren(List<ResCinemasChild> cinemasChildren) {
        this.cinemasChildren = cinemasChildren;
    }
}
