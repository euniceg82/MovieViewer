package com.exam.movieviewer.network.retrofit;

/**
 * Created by Eunice Galang on 5/30/2018.
 */

import com.exam.movieviewer.network.model.response.ResGetDetails;
import com.exam.movieviewer.network.model.response.ResGetSchedule;
import com.exam.movieviewer.network.model.response.ResGetSeatMap;

import retrofit2.Call;
import retrofit2.http.POST;

import static com.exam.movieviewer.utils.Constants.JSON_MOVIES;
import static com.exam.movieviewer.utils.Constants.JSON_SCHEDULE;
import static com.exam.movieviewer.utils.Constants.JSON_SEATS;

public interface APIService {

    @POST(JSON_SCHEDULE)
    Call<ResGetSchedule> getSchedule();

    @POST(JSON_MOVIES)
    Call<ResGetDetails> getDetails();

    @POST(JSON_SEATS)
    Call<ResGetSeatMap> getSeats();

}
