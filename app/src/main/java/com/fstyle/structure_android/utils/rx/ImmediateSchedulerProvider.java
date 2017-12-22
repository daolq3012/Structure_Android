package com.fstyle.structure_android.utils.rx;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by le.quang.dao on 28/03/2017.
 * Implementation of the {@link BaseSchedulerProvider} making all {@link Scheduler}s immediate.
 */

public class ImmediateSchedulerProvider implements BaseSchedulerProvider {

    @NonNull
    @Override
    public Scheduler computation() {
        return Schedulers.trampoline();
    }

    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return Schedulers.trampoline();
    }
}
