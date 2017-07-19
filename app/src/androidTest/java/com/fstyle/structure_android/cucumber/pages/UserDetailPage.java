package com.fstyle.structure_android.cucumber.pages;

import com.fstyle.structure_android.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Sun on 7/19/2017.
 */

public class UserDetailPage extends BasePage {
    public UserDetailPage() {
        onView(withId(R.id.user_detail_activity)).check(matches(isDisplayed()));
    }
}
