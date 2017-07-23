package com.fstyle.structure_android.cucumber.steps;

/**
 * Created by DaoLQ on 19/07/2017.
 * Fstyle Ltd
 * daole.2511@gmail.com
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Debug;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.fstyle.structure_android.cucumber.pages.BasePage;
import com.fstyle.structure_android.cucumber.pages.MainPage;
import com.fstyle.structure_android.cucumber.pages.SearchResultPage;
import com.fstyle.structure_android.screen.BaseActivity;
import com.fstyle.structure_android.screen.main.MainActivity;
import com.fstyle.structure_android.util.ActivityFinisher;
import com.fstyle.structure_android.util.CountingIdlingResourceListenerImpl;
import cucumber.api.CucumberOptions;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Rule;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Sun on 5/7/2017.
 */
@CucumberOptions(features = "features/search.feature")
@RunWith(AndroidJUnit4.class)
public class StepDefinitions {

    private BasePage mCurrentPage;
    private Activity mActivity;
    private CountingIdlingResourceListenerImpl mCountingIdlingResourceListener;

    @Rule
    private ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class, false, false);

    private void registerIdlingResources() {
        mCountingIdlingResourceListener = new CountingIdlingResourceListenerImpl("RequestAPI");
        ((BaseActivity) mActivity).setIdlingNotificationListener(mCountingIdlingResourceListener);
        Espresso.registerIdlingResources(
                mCountingIdlingResourceListener.getCountingIdlingResource());
    }

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityRule.launchActivity(
                new Intent()); // Start Activity before each test scenario
        registerIdlingResources();
        assertNotNull(mActivity);
    }

    /**
     * All the clean up of application's data and state after each scenario must happen here
     * The last call of this method should always be the call to parent's tear down method
     */
    @After
    public void tearDown() throws Exception {
        ((BaseActivity) mActivity).setIdlingNotificationListener(null);
        Espresso.unregisterIdlingResources(
                mCountingIdlingResourceListener.getCountingIdlingResource());
        ActivityFinisher.finishOpenActivities(); // Required for testing App with multiple
    }

    @Given("^I wait for manual attachment of the debugger$")
    public void wait_for_manual_attachment_of_debugger() throws InterruptedException {
        while (!Debug.isDebuggerConnected()) {
            Thread.sleep(1000);
        }
    }

    @Given("^I have a MainActivity$")
    public void I_have_a_MainActivity() {
        mCurrentPage = new MainPage();
    }

    @When("^I input keyword \"([^\"]*)\" and limit \"([^\"]*)\"$")
    public void iInputKeywordKeywordAndLimitLimit(String keyword, String limit) {
        mCurrentPage.is(MainPage.class).inputKeywordAndLimit(keyword, limit);
    }

    @And("^I click button search$")
    public void iClickButtonSearch() {
        mCurrentPage.is(MainPage.class).doSearch();
    }

    @Then("^I should see (\\S+)$")
    public void iShouldSeeErrorOnViewView(String errorMessage) throws Throwable {
        mCurrentPage.is(MainPage.class).seeError(errorMessage);
    }

    @Then("^I see result Screen$")
    public void iGotoResultScreen() throws Throwable {
        mCurrentPage = mCurrentPage.is(MainPage.class).seeSearchResultScreen();
        mCurrentPage.is(SearchResultPage.class);
    }
}
