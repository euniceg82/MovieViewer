package com.exam.movieviewer.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eunice Galang on 5/30/2018.
 */

public class ResGetSeatMap {
    @SerializedName("seat_count")
    @Expose
    public String seatCount;
    @SerializedName("seatmap")
    @Expose
    public List<ArrayList<String>> seatMap;
    public ResSeats available;

    public String getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(String seatCount) {
        this.seatCount = seatCount;
    }

    public List<ArrayList<String>> getSeatMap() {
        return seatMap;
    }

    public void setSeatMap(List<ArrayList<String>> seatMap) {
        this.seatMap = seatMap;
    }

    public ResSeats getAvailable() {
        return available;
    }

    public void setAvailable(ResSeats available) {
        this.available = available;
    }
}
