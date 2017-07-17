package com.fstyle.structure_android.screen.main

import com.fstyle.structure_android.AppComponent
import com.fstyle.structure_android.utils.dagger.ActivityScope

import dagger.Component

/**
 * Created by le.quang.dao on 21/03/2017.
 */

@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(MainModule::class))
interface MainComponent {
  fun inject(mainActivity: MainActivity)
}
