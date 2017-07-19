package com.fstyle.structure_android.cucumber.runner;

import android.os.Bundle;
import com.fstyle.structure_android.BuildConfig;
import cucumber.api.android.CucumberInstrumentationCore;

/**
 * Created by DaoLQ on 19/07/2017.
 * Fstyle Ltd
 * daole.2511@gmail.com
 */

public class CucumberTestRunner extends android.support.test.runner.AndroidJUnitRunner {

    public static final String TAG = CucumberTestRunner.class.getSimpleName();
    /**
     * This is the item Cucumber uses to identify the tags parameter, see method
     * cucumber-android-1.2.2.jar\cucumber\runtime\android\Arguments.class @
     * getCucumberOptionsString()
     */
    private static final String CUCUMBER_TAGS_KEY = "tags";
    private final CucumberInstrumentationCore instrumentationCore =
            new CucumberInstrumentationCore(this);

    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        // Read tags passed as parameters and overwrite @CucumberOptions tags inside
        // CucumberTestCase.java
        final String tags = BuildConfig.TEST_TAGS;
        if (!tags.isEmpty()) {
            // Reformat tags list to separate items with '--' as expected by Cucumber library,
            // see method
            // cucumber-android-1.2.2.jar\cucumber\runtime\android\Arguments.class @ appendOption()
            bundle.putString(CUCUMBER_TAGS_KEY, tags.replaceAll(",", "--").replaceAll("\\s", ""));
        }
        instrumentationCore.create(bundle);
    }

    @Override
    public void onStart() {
        waitForIdleSync();
        instrumentationCore.start();
    }
}
