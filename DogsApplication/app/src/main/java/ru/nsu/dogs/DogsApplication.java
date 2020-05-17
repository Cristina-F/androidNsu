package ru.nsu.dogs;

import android.app.Application;

public class DogsApplication extends Application {
    public static DogsApplication application;

    public static DogsApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;
    }
}
