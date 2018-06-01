package com.exam.movieviewer.module;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.exam.movieviewer.R;
import com.exam.movieviewer.app.BaseActivity;
import com.exam.movieviewer.network.model.response.ResGetDetails;
import com.exam.movieviewer.network.retrofit.APIService;
import com.exam.movieviewer.network.retrofit.RestClient;
import com.exam.movieviewer.utils.Utility;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Eunice Galang on 5/30/2018.
 */

public class MovieDetailsActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_poster) ImageView iv_poster;
    @BindView(R.id.iv_cover) ImageView iv_cover;
    @BindView(R.id.tv_name) TextView tv_name;
    @BindView(R.id.tv_genre) TextView tv_genre;
    @BindView(R.id.tv_rating) TextView tv_rating;
    @BindView(R.id.tv_duration) TextView tv_duration;
    @BindView(R.id.tv_release_date) TextView tv_release_date;
    @BindView(R.id.tv_synopsis) TextView tv_synopsis;
    @BindView(R.id.tv_cast) TextView tv_cast;
    @BindView(R.id.btn_next) Button btn_next;

    APIService apiService;
    String theatre;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_movie_details;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiService = RestClient.getInstance().getmAPIService();

        if (Utility.isNetworkAvailable(this)) getDetails();
        btn_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                if(theatre != null) {
                    Intent intent = new Intent(getApplicationContext(), BookMovieActivity.class);
                    intent.putExtra("theatre", theatre);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                break;
        }
    }

    public void getDetails() {
        apiService.getDetails().enqueue(new Callback<ResGetDetails>() {
            @Override
            public void onResponse(Call<ResGetDetails> call, Response<ResGetDetails> response) {

                if (response.isSuccessful()) {

                    ResGetDetails details = response.body();

                    theatre = details.getTheatre();

                    setTextContent(details.getCanonicalTitle(), tv_name);
                    setTextContent(details.getGenre(), tv_genre);
                    setTextContent(details.getAdvisoryRating(), tv_rating);
                    setTextContent(Utility.convertReadableTime(details.getRuntimeMins()), tv_duration);
                    setTextContent(Utility.convertDate(details.getReleaseDate()), tv_release_date);
                    setTextContent(details.getSynopsis(), tv_synopsis);
                    setTextContent(details.getCast().toString(), tv_cast);
                    loadImage(details.getPosterLandscape(), iv_poster);
                    loadImage(details.getPoster(), iv_cover);

                } else {
                    Toasty.error(getApplicationContext(), response.message(), Toast.LENGTH_LONG, true).show();
                }

            }

            @Override
            public void onFailure(Call<ResGetDetails> call, Throwable t) {
                Toasty.error(getApplicationContext(), t.toString(), Toast.LENGTH_LONG, true).show();
            }
        });
    }

    public void loadImage(String url, ImageView imageView) {
        Picasso picasso = Picasso.with(getContext());
        picasso.load(url)
                .fit()
                .placeholder(R.drawable.ic_blank_image)
                .into(imageView);
    }

    public void setTextContent(String text, TextView textView) {
        if (text.contains("[")) text = text.replace("[", "").replace("]", "");
        textView.setText(text);
    }
}
