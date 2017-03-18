package com.fstyle.structure_android.screen.searchresult;

import android.app.Activity;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.utils.Constant;
import com.fstyle.structure_android.utils.dagger.ActivityScope;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sun on 3/19/2017.
 */

@Module
public class SearchResultModule {

    private SearchResultContract.View mView;

    public SearchResultModule(SearchResultContract.View view) {
        this.mView = view;
    }

    @ActivityScope
    @Provides
    public SearchResultContract.Presenter providePresenter() {
        return new SearchResultPresenter(mView);
    }

    @ActivityScope
    @Provides
    public SearchResultAdapter provideSearchResultAdapter() {
        Activity activity = (Activity) mView;
        ArrayList<User> users = activity.getIntent().getParcelableArrayListExtra(Constant
                .LIST_USER_ARGS);
        return new SearchResultAdapter(activity, users);
    }
}
