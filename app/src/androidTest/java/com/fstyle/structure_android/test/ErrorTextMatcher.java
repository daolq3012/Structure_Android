package com.fstyle.structure_android.test;

/**
 * Created by framgia on 15/05/2017.
 */

import android.support.design.widget.TextInputLayout;
import android.view.View;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Custom matcher to assert equal TextInputLayout.setError();
 */
public class ErrorTextMatcher extends TypeSafeMatcher<View> {

    private String mExpectedError;

    public ErrorTextMatcher(String expectedError) {
        mExpectedError = expectedError;
    }

    @Override
    protected boolean matchesSafely(View view) {
        if (view instanceof TextInputLayout) {
            TextInputLayout textInputLayout = (TextInputLayout) view;
            return mExpectedError.equals(textInputLayout.getError());
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {

    }
}
