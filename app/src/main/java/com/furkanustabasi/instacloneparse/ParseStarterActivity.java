package com.furkanustabasi.instacloneparse;

import android.app.Application;

import com.parse.Parse;

public class ParseStarterActivity extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("41gkKmkCTbp3zBEqI3phfeoNERYDzkJyCcLW8Yjf")
                .clientKey("KrW7nHaT5cswcwNiUEUFdLKmHPHiFRdONgJCo4PR")
                .server("https://parseapi.back4app.com/")
                .build()
        );

    }
}
