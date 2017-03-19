package com.fstyle.structure_android.screen.searchresult;

import com.fstyle.structure_android.AppComponent;
import com.fstyle.structure_android.utils.dagger.ActivityScope;

import dagger.Component;

/**
 * Created by Sun on 3/19/2017.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = SearchResultModule.class)
public interface SearchResultComponent {
    void inject(SearchResultActivity searchResultActivity);
}
