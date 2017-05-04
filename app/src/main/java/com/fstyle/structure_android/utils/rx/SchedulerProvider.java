package com.fstyle.structure_android.utils.rx;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by le.quang.dao on 28/03/2017.
 * Provides different types of schedulers.
 */

public final class SchedulerProvider implements BaseSchedulerProvider {
    @Nullable
    private static SchedulerProvider mInstance;

    // Prevent direct instantiation.
    private SchedulerProvider() {
    }

    public static synchronized SchedulerProvider getInstance() {
        if (mInstance == null) {
            mInstance = new SchedulerProvider();
        }
        return mInstance;
    }

    @Override
    @NonNull
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @Override
    @NonNull
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    @NonNull
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}
