package com.fstyle.structure_android.cucumber.pages;

import com.fstyle.structure_android.R;

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
}
