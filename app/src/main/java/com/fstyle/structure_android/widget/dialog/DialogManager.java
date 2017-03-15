package com.fstyle.structure_android.widget.dialog;

import android.content.DialogInterface;

/**
 * Created by le.quang.dao on 14/03/2017.
 */

public interface DialogManager {

    MainAlertDialog getDialog();

    boolean isShowing();

    void dismiss();

    /**
     * <h1>Common Dialog show message from API with positive button click listener</h1>
     * <a href="http://imgur.com/RbODnvE"><img src="http://i.imgur.com/RbODnvE.png" title="source:
     * imgur.com" /></a>
     */
    void dialogMainStyle(String message, DialogInterface.OnClickListener positiveButtonListener);
}
