package com.fstyle.structure_android.cucumber.steps;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Sun on 7/19/2017.
 * Runs all instrumentation tests from one place
 */

@RunWith(Suite.class)
@Suite.SuiteClasses ({
        StepDefinitions.class,
        SearchResultSteps.class,
})
public class AllTestsSuite {
}
