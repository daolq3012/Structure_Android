package com.fstyle.structure_android.utils.rx

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * Created by le.quang.dao on 28/03/2017.
 */

class ImmediateSchedulerProvider : BaseSchedulerProvider {

  override fun computation(): Scheduler {
    return Schedulers.trampoline()
  }

  override fun io(): Scheduler {
    return Schedulers.trampoline()
  }

  override fun ui(): Scheduler {
    return Schedulers.trampoline()
  }
}
