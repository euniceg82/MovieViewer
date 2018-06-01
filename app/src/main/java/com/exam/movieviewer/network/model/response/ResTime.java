package com.exam.movieviewer.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Eunice Galang on 5/30/2018.
 */

public class ResTime {
    public String parent;
    @SerializedName("times")
    @Expose
    public List<ResTimeChild> timeChildren;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public List<ResTimeChild> getTimeChildren() {
        return timeChildren;
    }

    public void setTimeChildren(List<ResTimeChild> timeChildren) {
        this.timeChildren = timeChildren;
    }
}
