package com.exam.movieviewer.network.model.response;

/**
 * Created by Eunice Galang on 5/30/2018.
 */

public class ResCinemasChild {
    public String id;
    public String cinema_id;
    public String label;

    public ResCinemasChild(String id, String cinema_id, String label){
        super();
        this.id = id;
        this.cinema_id = cinema_id;
        this.label = label;
    }

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

    public String getCinema_id() {
        return cinema_id;
    }

    public void setCinema_id(String cinema_id) {
        this.cinema_id = cinema_id;
    }

    public String toString(){
        return label;
    }
}
