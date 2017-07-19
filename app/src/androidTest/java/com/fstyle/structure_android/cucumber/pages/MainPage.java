package com.fstyle.structure_android.cucumber.pages;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import com.fstyle.structure_android.R;
import com.fstyle.structure_android.util.ErrorTextMatcher;
import com.fstyle.structure_android.utils.Constant;

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

public class MainPage extends BasePage {

    /**
     * The constructor verifies that we are on the correct page by checking
     * the existence of the unique identifier elements of the page/view
     */
    public MainPage() {
        onView(withId(R.id.main_activity)).check(matches(isDisplayed()));
    }

    public void inputKeywordAndLimit(String keyword, String limit) {
        Espresso.onView(ViewMatchers.withId(R.id.edtKeyword))
                .perform(ViewActions.typeText(keyword));
        Espresso.onView(ViewMatchers.withId(R.id.edtNumberLimit))
                .perform(ViewActions.typeText(limit));
        closeSoftKeyboard();
    }

    public void doSearch() {
        Espresso.onView(ViewMatchers.withText("Search")).perform(ViewActions.click());
    }

    public void seeError(String error) {
        String[] errors = error.split("_");
        checkErrorKeywordInput(errors[0]);
        checkErrorLimitNumberInput(errors[1]);
    }

    private void checkErrorKeywordInput(String error) {
        if (!Constant.NONE.equals(error)) {
            int errorResourceId;
            if (Constant.EMPTY.equals(error)) {
                errorResourceId = R.string.must_not_empty;
            } else {
                errorResourceId = R.string.error_unusable_characters;
            }
            Espresso.onView(ViewMatchers.withId(R.id.txtInputLayoutKeyword))
                    .check(ViewAssertions.matches(ErrorTextMatcher.hasErrorText(errorResourceId)));
        }
    }

    private void checkErrorLimitNumberInput(String error) {
        if (!Constant.NONE.equals(error)) {
            int errorResourceId;
            if (Constant.EMPTY.equals(error)) {
                errorResourceId = R.string.must_not_empty;
            } else {
                errorResourceId = R.string.error_lenght_from_0_to_100;
            }
            Espresso.onView(ViewMatchers.withId(R.id.txtInputLayoutNumberLimit))
                    .check(ViewAssertions.matches(ErrorTextMatcher.hasErrorText(errorResourceId)));
        }
    }

    public SearchResultPage seeSearchResultScreen() {
        return new SearchResultPage();
    }
}
