package com.fstyle.structure_android.screen.main;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

class MainPresenter implements MainContract.Presenter {

    private final MainContract.View mMainView;

    MainPresenter(MainContract.View view) {
        mMainView = view;
        mMainView.setPresenter(this);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
