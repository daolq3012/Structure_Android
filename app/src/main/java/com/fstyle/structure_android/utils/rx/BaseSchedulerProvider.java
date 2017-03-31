package com.fstyle.structure_android.utils.rx;

import android.support.annotation.NonNull;
import rx.Scheduler;

/**
 * Created by le.quang.dao on 29/03/2017.
 * Allow providing different types of {@link Scheduler}s.
 */

public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}
