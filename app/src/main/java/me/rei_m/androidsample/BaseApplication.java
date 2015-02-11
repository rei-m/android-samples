package me.rei_m.androidsample;

import android.app.Application;

import me.rei_m.androidsample.model.ModelLocator;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ModelLocator.getInstance().initialize();
    }
}
