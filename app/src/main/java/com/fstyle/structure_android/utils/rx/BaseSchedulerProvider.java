package com.fstyle.structure_android.utils.rx;

import android.support.annotation.NonNull;
import rx.Scheduler;

/**
 * Created by le.quang.dao on 28/03/2017.
 */

public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}
