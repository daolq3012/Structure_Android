package com.fstyle.structure_android.util;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by DaoLQ on 19/07/2017.
 * Fstyle Ltd
 * daole.2511@gmail.com
 *
 * Custom matcher to assert equal TextInputLayout.setError();
 */
public class ErrorTextMatcher extends TypeSafeMatcher<View> {

    private int mExpectedErrorResourceId;

    public ErrorTextMatcher(int expectedErrorResourceId) {
        mExpectedErrorResourceId = expectedErrorResourceId;
    }

    @Override
    protected boolean matchesSafely(View view) {
        if (view instanceof TextInputLayout) {
            TextInputLayout textInputLayout = (TextInputLayout) view;
            return view.getContext()
                    .getString(mExpectedErrorResourceId)
                    .equals(textInputLayout.getError());
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {

    }

    public static Matcher<? super View> hasErrorText(int expectedErrorResourceId) {
        return new ErrorTextMatcher(expectedErrorResourceId);
    }
}
