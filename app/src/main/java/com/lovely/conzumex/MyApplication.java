package com.lovely.conzumex;

import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;

public class MyApplication extends Application {

    private static WeakReference<Context> mWeakReference;

    public static Context getAppContext() {
        return mWeakReference.get();
    }

}
