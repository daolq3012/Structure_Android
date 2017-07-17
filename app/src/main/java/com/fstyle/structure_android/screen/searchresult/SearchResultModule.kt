package com.fstyle.structure_android.screen.searchresult

import android.app.Activity
import com.fstyle.structure_android.data.model.User
import com.fstyle.structure_android.utils.Constant
import com.fstyle.structure_android.utils.dagger.ActivityScope
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
      adapter: SearchResultAdapter): SearchResultContract.ViewModel {
    return SearchResultViewModel(presenter, adapter)
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
}
