package com.fstyle.structure_android.screen;

/**
 * BasePresenter
 *
 * @param <T> class extend from BaseViewModel
 */

public interface BasePresenter<T extends BaseViewModel> {

    void onStart();

    void onStop();

    void setViewModel(T viewModel);
}
