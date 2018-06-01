package com.exam.movieviewer.network.model.response;

/**
 * Created by Eunice Galang on 5/30/2018.
 */

public class ResDates {
    public String id;
    public String label;
    public String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toString(){
        return label;
    }
}
