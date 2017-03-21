package com.fstyle.structure_android.screen.searchresult;

import android.app.Activity;
import android.support.annotation.NonNull;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.utils.Constant;
import com.fstyle.structure_android.utils.dagger.ActivityScope;
import dagger.Module;
import dagger.Provides;
import java.util.ArrayList;

/**
 * Created by Sun on 3/19/2017.
 */

@Module
public class SearchResultModule {

    private Activity mActivity;

    public SearchResultModule(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    public SearchResultContract.ViewModel provideViewModel(SearchResultContract.Presenter presenter,
            SearchResultAdapter adapter) {
        return new SearchResultViewModel(presenter, adapter);
    }

    @ActivityScope
    @Provides
    public SearchResultContract.Presenter providePresenter() {
        return new SearchResultPresenter();
    }

    @ActivityScope
    @Provides
    public SearchResultAdapter provideSearchResultAdapter() {
        ArrayList<User> users =
                mActivity.getIntent().getParcelableArrayListExtra(Constant.ARGUMENT_LIST_USER);
        return new SearchResultAdapter(mActivity, users);
    }
}
