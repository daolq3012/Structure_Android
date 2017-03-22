package com.fstyle.structure_android.screen.searchresult;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import com.fstyle.structure_android.MainApplication;
import com.fstyle.structure_android.R;
import com.fstyle.structure_android.databinding.ActivitySearchResultBinding;
import com.fstyle.structure_android.screen.BaseActivity;
import javax.inject.Inject;

/**
 * SearchResult Screen.
 */
public class SearchResultActivity extends BaseActivity {

    @Inject
    SearchResultContract.ViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DaggerSearchResultComponent.builder()
                .appComponent(((MainApplication) getApplication()).getAppComponent())
                .searchResultModule(new SearchResultModule(this))
                .build()
                .inject(this);

        super.onCreate(savedInstanceState);

        ActivitySearchResultBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_search_result);
        binding.setViewModel((SearchResultViewModel) mViewModel);
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    public void onStop() {
        mViewModel.onStop();
        super.onStop();
    }
}
