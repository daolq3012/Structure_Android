package com.fstyle.structure_android.test.screen.main;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import com.fstyle.structure_android.R;
import com.fstyle.structure_android.screen.main.MainActivity;
import com.fstyle.structure_android.test.ErrorTextMatcher;
import cucumber.api.CucumberOptions;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Matcher;

/**
 * Created by Sun on 5/7/2017.
 */
@CucumberOptions(features = "features/main.feature")
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest(MainActivity activityClass) {
        super(MainActivity.class);
    }

    @Given("^I have a MainActivity")
    public void I_have_a_MainActivity() {
        assertNotNull(getActivity());
    }

    @When("^I input keyword (\\S+)$")
    public void I_input_keyword(final String keyword) {
        Espresso.onView(ViewMatchers.withId(R.id.edtKeyword))
                .perform(ViewActions.typeText(keyword));
    }

    @When("^I input limit number (\\S+)$")
    public void I_input_limit_number(final String limit) {
        Espresso.onView(ViewMatchers.withId(R.id.edtNumberLimit))
                .perform(ViewActions.typeText(limit));
    }

    @Then("^I should see error on the (\\S+)$")
    public void i_should_see_error_on_the_keyword(final String view) {
        boolean isKeywordView = "keyword".equals(view);
        int viewId = isKeywordView ? R.id.txtInputLayoutKeyword : R.id.txtInputLayoutNumberLimit;
        int errorResourceId = isKeywordView ? R.string.error_unusable_characters
                : R.string.error_lenght_from_0_to_100;

        Espresso.onView(ViewMatchers.withId(viewId))
                .check(ViewAssertions.matches(
                        MainActivityTest.hasErrorText(getActivity().getString(errorResourceId))));
    }

    //    @Then("^I should (true|false) Result Screen")
    //    public void i_should_true_result_screen(final boolean shouldSeeError) {
    //    }

    private static Matcher<? super View> hasErrorText(String expectedError) {
        return new ErrorTextMatcher(expectedError);
    }
}