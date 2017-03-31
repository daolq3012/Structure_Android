package com.fstyle.structure_android.utils.rx;

import android.support.annotation.NonNull;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by le.quang.dao on 28/03/2017.
 * Implementation of the {@link BaseSchedulerProvider} making all {@link Scheduler}s immediate.
 */

public class ImmediateSchedulerProvider implements BaseSchedulerProvider {

    @NonNull
    @Override
    public Scheduler computation() {
        return Schedulers.immediate();
    }

    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.immediate();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return Schedulers.immediate();
    }
}
