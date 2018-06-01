package com.exam.movieviewer.module;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exam.movieviewer.R;
import com.exam.movieviewer.app.BaseActivity;
import com.exam.movieviewer.module.adapter.SeatAdapter;
import com.exam.movieviewer.module.adapter.SelectedSeatAdapter;
import com.exam.movieviewer.network.model.response.ResCinemas;
import com.exam.movieviewer.network.model.response.ResCinemasChild;
import com.exam.movieviewer.network.model.response.ResDates;
import com.exam.movieviewer.network.model.response.ResGetSchedule;
import com.exam.movieviewer.network.model.response.ResGetSeatMap;
import com.exam.movieviewer.network.model.response.ResSeats;
import com.exam.movieviewer.network.model.response.ResTime;
import com.exam.movieviewer.network.model.response.ResTimeChild;
import com.exam.movieviewer.network.retrofit.APIService;
import com.exam.movieviewer.network.retrofit.RestClient;
import com.exam.movieviewer.utils.Utility;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Eunice Galang on 5/30/2018.
 */

public class BookMovieActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.gridView) GridView gridView;
    @BindView(R.id.tv_theatre) TextView tv_theatre;
    @BindView(R.id.et_date) EditText et_date;
    @BindView(R.id.et_cinemas) EditText et_cinemas;
    @BindView(R.id.et_time) EditText et_time;
    @BindView(R.id.ll_parent) LinearLayout ll_parent;
    @BindView(R.id.gv_selected) GridView gv_selected;
    @BindView(R.id.tv_total) TextView tv_total;

    @BindString(R.string.warning_unavailable) String cinemaFull;
    @BindString(R.string.warning_cinema_empty) String cinemaEmpty;
    @BindString(R.string.warning_date_empty) String dateEmpty;
    @BindString(R.string.warning_full) String warning_full;
    @BindString(R.string.warning_select_sched) String warning_select_sched;

    APIService apiService;

    ArrayAdapter<ResDates> dateAdapter;
    ArrayAdapter<ResCinemasChild> cinemaAdapter;
    ArrayAdapter<ResTimeChild> timeAdapter;

    List<ResCinemasChild> resCinemasChildren = new ArrayList<>();
    List<ResTimeChild> resTimeChildren = new ArrayList<>();
    List<ResCinemas> cinemasList = new ArrayList<>();
    List<ResDates> dateList = new ArrayList<>();
    List<ResTime> timeList = new ArrayList<>();

    List<String> selectedSeats = new ArrayList<>();

    HashMap<String, List<ResCinemasChild>> cinemaHashMap = new HashMap<String, List<ResCinemasChild>>();
    HashMap<String, List<ResTimeChild>> timeHashMap = new HashMap<String, List<ResTimeChild>>();

    String selectedCinema;
    String selectedDate;
    int price;


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_book_movie;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiService = RestClient.getInstance().getmAPIService();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tv_theatre.setText(bundle.getString("theatre"));
        }

        init();
    }

    public void init() {
        if (Utility.isNetworkAvailable(this)) {
            getSeats();
            getSchedule();
        }

        et_date.setOnClickListener(this);
        et_cinemas.setOnClickListener(this);
        et_time.setOnClickListener(this);
    }

    public void getSeats() {
        apiService.getSeats().enqueue(new Callback<ResGetSeatMap>() {
            @Override
            public void onResponse(Call<ResGetSeatMap> call, Response<ResGetSeatMap> response) {

                if (response.isSuccessful()) {
                    List<ArrayList<String>> genArray = new ArrayList<ArrayList<String>>();
                    genArray = response.body().getSeatMap();

                    List<String> seatsList = new ArrayList<String>();
                    List<String> newSeatsList = new ArrayList<String>();

                    for (int i = 0; i < genArray.size(); i++) {
                        seatsList = genArray.get(i);

                        for (int e = 0; e < seatsList.size(); e++) {
                            newSeatsList.add(seatsList.get(e));
                        }
                    }

                    ResSeats resSeats = response.body().getAvailable();
                    List<String> availableList = new ArrayList<String>();
                    availableList = resSeats.getSeats();

                    showGridSeats(newSeatsList, seatsList.size(), availableList);

                } else {
                    Toasty.error(getApplicationContext(), response.message(), Toast.LENGTH_LONG, true).show();
                }

            }

            @Override
            public void onFailure(Call<ResGetSeatMap> call, Throwable t) {
                Toasty.error(getApplicationContext(), t.toString(), Toast.LENGTH_LONG, true).show();
            }
        });
    }

    public void getSchedule() {
        apiService.getSchedule().enqueue(new Callback<ResGetSchedule>() {
            @Override
            public void onResponse(Call<ResGetSchedule> call, Response<ResGetSchedule> response) {

                if (response.isSuccessful()) {
                    dateList = response.body().getDates();
                    cinemasList = response.body().getCinemas();
                    timeList = response.body().getTime();

                    dateAdapter = new ArrayAdapter<ResDates>(BookMovieActivity.this,
                            android.R.layout.simple_dropdown_item_1line, dateList);

                    getCinemaChidren(response.body().getCinemas());
                    getTimeChidren(response.body().getTime());
                } else {
                    Toasty.error(getApplicationContext(), response.message(), Toast.LENGTH_LONG, true).show();
                }

            }

            @Override
            public void onFailure(Call<ResGetSchedule> call, Throwable t) {
                Toasty.error(getApplicationContext(), t.toString(), Toast.LENGTH_LONG, true).show();
            }
        });
    }

    public void getCinemaChidren(List<ResCinemas> categories) {
        resCinemasChildren = new ArrayList<>();
        for (ResCinemas cinemaChildren : categories) {
            List<ResCinemasChild> resCinemasChildList = cinemaChildren.getCinemasChildren();
            cinemaHashMap.put(cinemaChildren.getParent(), resCinemasChildList);

            resCinemasChildren.add(new ResCinemasChild(
                    resCinemasChildList.get(0).getId(),
                    resCinemasChildList.get(0).getCinema_id(),
                    resCinemasChildList.get(0).getLabel()));
        }
        cinemaAdapter = new ArrayAdapter<ResCinemasChild>(BookMovieActivity.this,
                android.R.layout.simple_dropdown_item_1line, resCinemasChildren);
    }

    public void getTimeChidren(List<ResTime> categories) {
        resTimeChildren = new ArrayList<>();
        for(ResTime timeChildren: categories){
            List<ResTimeChild> resTimeChildList = timeChildren.getTimeChildren();
            timeHashMap.put(timeChildren.getParent(), resTimeChildList);

            resTimeChildren.add(new ResTimeChild(
                    resTimeChildList.get(0).getId(),
                    resTimeChildList.get(0).getPrice(),
                    resTimeChildList.get(0).getLabel()));
        }
        timeAdapter = new ArrayAdapter<ResTimeChild>(BookMovieActivity.this,
                android.R.layout.simple_dropdown_item_1line, resTimeChildren);
    }

    public void showGridSeats(List<String> newSeats, int count, List<String> available) {
        final SeatAdapter adapter = new SeatAdapter(BookMovieActivity.this, R.id.gridView, newSeats, available);
        gridView.setAdapter(adapter);
        gridView.setNumColumns(count);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                String item = adapter.getItem(position);

                if (et_date.getText().length() != 0 && et_cinemas.getText().length() != 0 && et_time.getText().length() != 0) {
                    if (selectedSeats.contains(item)) {
                        ((ImageView) v).setImageResource(R.drawable.ic_check_box_outline_blank);
                        selectedSeats.remove(item);
                    } else {
                        if (selectedSeats.size() == 10)
                            Toasty.warning(getApplicationContext(), warning_full, Toast.LENGTH_SHORT, true).show();
                        else {
                            ((ImageView) v).setImageResource(R.drawable.ic_check_box);
                            selectedSeats.add(item);
                        }
                    }
                    gridSelected();
                    computeTotal();
                } else
                    Toasty.error(getApplicationContext(), warning_select_sched, Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    public void computeTotal() {
        int seatCount = selectedSeats.size();
        double total = seatCount * price;

        DecimalFormat formatter = new DecimalFormat("#,###.00");

        tv_total.setText("Php " + formatter.format(total));
    }

    public void gridSelected() {
        SelectedSeatAdapter adapter = new SelectedSeatAdapter(BookMovieActivity.this, R.id.gv_selected, selectedSeats);
        gv_selected.setAdapter(adapter);
        gv_selected.setNumColumns(5);
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(BookMovieActivity.this);
        AlertDialog alert = null;
        switch (v.getId()) {
            case R.id.et_date:
                if (dateList.size() != 0) {
                    builder.setAdapter(dateAdapter,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int item) {
                                    selectedDate = dateAdapter.getItem(item).getId();
                                    et_date.setText(dateAdapter.getItem(item).getLabel());
                                    et_cinemas.setText(null);
                                    et_time.setText(null);
                                    dialog.dismiss();
                                }
                            });
                    alert = builder.create();
                } else
                    Toasty.error(getApplicationContext(), dateEmpty, Toast.LENGTH_SHORT, true).show();
                break;
            case R.id.et_cinemas:
                try {
                    if (cinemaHashMap.containsKey(selectedDate)) {
                        cinemaAdapter = new ArrayAdapter<ResCinemasChild>(BookMovieActivity.this,
                                android.R.layout.simple_dropdown_item_1line, cinemaHashMap.get(selectedDate));
                        builder.setAdapter(cinemaAdapter,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int item) {
                                        selectedCinema = cinemaAdapter.getItem(item).getId();
                                        et_cinemas.setText(cinemaAdapter.getItem(item).getLabel());
                                        et_time.setText(null);
                                        dialog.dismiss();
                                    }
                                });
                        alert = builder.create();
                    } else {
                        Toasty.error(getApplicationContext(), cinemaEmpty, Toast.LENGTH_SHORT, true).show();
                        et_cinemas.setText(null);
                    }
                } catch (Exception ex) {
                }

                break;
            case R.id.et_time:
                try {
                    if (timeHashMap.containsKey(selectedCinema)) {
                        timeAdapter = new ArrayAdapter<ResTimeChild>(BookMovieActivity.this,
                                android.R.layout.simple_dropdown_item_1line, timeHashMap.get(selectedCinema));
                        builder.setAdapter(timeAdapter,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int item) {
                                        et_time.setText(timeAdapter.getItem(item).getLabel());
                                        price = Integer.parseInt(timeAdapter.getItem(item).getPrice());
                                        dialog.dismiss();
                                    }
                                });
                        alert = builder.create();
                    } else {
                        Toasty.error(getApplicationContext(), cinemaFull, Toast.LENGTH_SHORT, true).show();
                        et_time.setText(null);
                    }
                } catch (Exception ex) {
                }

                break;
        }
        if (alert != null) alert.show();
    }

}
