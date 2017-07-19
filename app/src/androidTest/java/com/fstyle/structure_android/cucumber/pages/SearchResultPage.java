package com.fstyle.structure_android.cucumber.pages;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import com.fstyle.structure_android.R;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by DaoLQ on 19/07/2017.
 * Fstyle Ltd
 * daole.2511@gmail.com
 */

public class SearchResultPage extends BasePage {
    public SearchResultPage() {
        onView(withId(R.id.search_result_activity)).check(matches(isDisplayed()));
    }

    public void clickFirstItems() {
        onView(withId(R.id.recycler_search_result)).check(matches(isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
    }

    public UserDetailPage seeUserDetailScreen() {
        return new UserDetailPage();
    }
}
