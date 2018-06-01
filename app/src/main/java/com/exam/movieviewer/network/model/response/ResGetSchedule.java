package com.exam.movieviewer.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Eunice Galang on 5/30/2018.
 */

public class ResGetSchedule {
    public List<ResDates> dates;
    public List<ResCinemas> cinemas;
    @SerializedName("times")
    @Expose
    public List<ResTime> time;

    public List<ResDates> getDates() {
        return dates;
    }

    public void setDates(List<ResDates> dates) {
        this.dates = dates;
    }

    public List<ResCinemas> getCinemas() {
        return cinemas;
    }

    public void setCinemas(List<ResCinemas> cinemas) {
        this.cinemas = cinemas;
    }

    public List<ResTime> getTime() {
        return time;
    }

    public void setTime(List<ResTime> time) {
        this.time = time;
    }
}
