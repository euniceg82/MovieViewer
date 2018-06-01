package com.exam.movieviewer.network.model.response;

/**
 * Created by Eunice Galang on 5/30/2018.
 */

public class ResTimeChild {
    public String id;
    public String price;
    public String label;

    public ResTimeChild(String id, String price, String label){
        super();
        this.id = id;
        this.price = price;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String toString(){
        return label;
    }
}
