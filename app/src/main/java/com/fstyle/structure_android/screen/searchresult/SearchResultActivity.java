package com.fstyle.structure_android.screen.searchresult;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import com.fstyle.structure_android.R;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.databinding.ActivitySearchResultBinding;
import com.fstyle.structure_android.screen.BaseActivity;
import com.fstyle.structure_android.utils.Constant;
import com.fstyle.structure_android.utils.navigator.Navigator;
import java.util.ArrayList;

/**
 * SearchResult Screen.
 */
public class SearchResultActivity extends BaseActivity {

    private SearchResultViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<User> users =
                getIntent().getParcelableArrayListExtra(Constant.ARGUMENT_LIST_USER);
        SearchResultAdapter searchResultAdapter = new SearchResultAdapter(this, users);
        mViewModel = new SearchResultViewModel(searchResultAdapter, new Navigator(this));

        ActivitySearchResultBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_search_result);
        binding.setViewModel(mViewModel);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    protected void onStop() {
        mViewModel.onStop();
        super.onStop();
    }
}
