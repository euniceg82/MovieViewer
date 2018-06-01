package com.exam.movieviewer.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;

/**
 * Created by Eunice Galang on 5/30/2018.
 */
public abstract class BaseActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        context = this;
        ButterKnife.bind(this);

    }

    public Context getContext() {
        return context;
    }

    protected abstract int getLayoutResourceId();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
