package com.fstyle.structure_android.cucumber.steps;

import android.app.Activity;
import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.fstyle.structure_android.cucumber.pages.BasePage;
import com.fstyle.structure_android.cucumber.pages.SearchResultPage;
import com.fstyle.structure_android.screen.BaseActivity;
import com.fstyle.structure_android.screen.main.MainActivity;
import com.fstyle.structure_android.screen.searchresult.SearchResultActivity;
import com.fstyle.structure_android.util.ActivityFinisher;
import com.fstyle.structure_android.util.CountingIdlingResourceListenerImpl;
import cucumber.api.CucumberOptions;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Rule;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Sun on 7/19/2017.
 */
//@CucumberOptions(features = "features/result.feature")
//@RunWith(AndroidJUnit4.class)
public class SearchResultSteps {

    private BasePage mCurrentPage;
    private Activity mActivity;

    @Rule
    private ActivityTestRule<SearchResultActivity> mActivityRule =
            new ActivityTestRule<>(SearchResultActivity.class, false, false);

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityRule.launchActivity(
                new Intent()); // Start Activity before each test scenario
        assertNotNull(mActivity);
    }

    /**
     * All the clean up of application's data and state after each scenario must happen here
     * The last call of this method should always be the call to parent's tear down method
     */
    @After
    public void tearDown() throws Exception {
        ActivityFinisher.finishOpenActivities(); // Required for testing App with multiple
    }

    @Given("^I have a SearchResult Activity$")
    public void iHaveASearchResultActivity() throws Throwable {
        mCurrentPage = new SearchResultPage();
    }

    @And("^I click any item$")
    public void iClickAnyItem() throws Throwable {
        mCurrentPage.is(SearchResultPage.class).clickFirstItems();
    }

    @Then("^I see detail Screen$")
    public void iSeeDetailScreen() throws Throwable {
        mCurrentPage.is(SearchResultPage.class).seeUserDetailScreen();
    }
}
