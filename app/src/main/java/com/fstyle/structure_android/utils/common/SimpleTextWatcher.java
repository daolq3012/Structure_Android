package com.fstyle.structure_android.utils.common;

import android.text.TextWatcher;

/**
 * Created by framgia on 27/04/2017.
 */

public abstract class SimpleTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // No-op
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // No-op
    }
}
