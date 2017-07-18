package com.fstyle.structure_android.screen.searchresult

import android.app.Activity
import com.fstyle.structure_android.data.model.User
import com.fstyle.structure_android.utils.Constant
import com.fstyle.structure_android.utils.dagger.ActivityScope
import com.fstyle.structure_android.utils.navigator.Navigator
import dagger.Module
import dagger.Provides

/**
 * Created by Sun on 3/19/2017.
 */

@Module
class SearchResultModule(private val mActivity: Activity) {

  @ActivityScope
  @Provides
  fun provideViewModel(presenter: SearchResultContract.Presenter,
      adapter: SearchResultAdapter, navigator: Navigator): SearchResultContract.ViewModel {
    return SearchResultViewModel(presenter, adapter,navigator)
  }

  @ActivityScope
  @Provides
  fun providePresenter(): SearchResultContract.Presenter {
    return SearchResultPresenter()
  }

  @ActivityScope
  @Provides
  fun provideSearchResultAdapter(): SearchResultAdapter {
    val users = mActivity.intent.getParcelableArrayListExtra<User>(
        Constant.ARGUMENT_LIST_USER)
    return SearchResultAdapter(mActivity, users)
  }

  @ActivityScope
  @Provides
  fun provideNavigator(): Navigator {
    return Navigator(mActivity)
  }
}
