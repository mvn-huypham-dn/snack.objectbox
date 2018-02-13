package com.huypham.snack.objectbox.pref;

import android.app.Application;

import com.huypham.snack.objectbox.BuildConfig;
import com.huypham.snack.objectbox.model.MyObjectBox;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

/**
 * Snack project_object box
 * Created by HuyPhamNA on 01/02/2018.
 */

public class App extends Application {
    private static App sApp;
    private BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        boxStore = MyObjectBox.builder().androidContext(App.this).build();
        if (BuildConfig.DEBUG) {
            new AndroidObjectBrowser(boxStore).start(this);
        }
    }

    public static App getApp() {
        return sApp;
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }
}
