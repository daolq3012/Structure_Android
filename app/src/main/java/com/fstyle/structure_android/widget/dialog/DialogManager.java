package com.fstyle.structure_android.widget.dialog;

import android.support.annotation.ArrayRes;
import android.support.annotation.DrawableRes;
import com.fstyle.library.MaterialDialog;

/**
 * Created by le.quang.dao on 14/03/2017.
 */

public interface DialogManager {

    /**
     * <h1>Indeterminate Progress Dialog</h1>
     * <img width="400" height="110" src="https://github.com/daolq3012/DialogManager/blob/master/image/IndeterminateProgressDialog.png?raw=true"
     * alt="">
     */
    void showIndeterminateProgressDialog();

    void dismissProgressDialog();

    void dialogError(String content, MaterialDialog.SingleButtonCallback positiveButtonListener);

    /**
     * <h1>BasicWithoutTitle Dialog</h1>
     * <img width="400" height="134" src="https://github.com/daolq3012/DialogManager/blob/master/image/BasicWithoutTitle.png?raw=true"
     * alt="">
     */
    void dialogBasicWithoutTitle(String content,
            MaterialDialog.SingleButtonCallback positiveButtonListener);

    /**
     * <h1>Basic Dialog</h1>
     * <img width="400" height="183" src="https://github.com/daolq3012/DialogManager/blob/master/image/Basic.png?raw=true"
     * alt="">
     */
    void dialogBasic(String title, String content,
            MaterialDialog.SingleButtonCallback positiveButtonListener);

    /**
     * <h1>BasicIcon Dialog</h1>
     * <img width="400" height="211" src="https://github.com/daolq3012/DialogManager/blob/master/image/BasicIcon.png?raw=true"
     * alt="">
     */
    void dialogBasicIcon(String title, String content, @DrawableRes int icon,
            MaterialDialog.SingleButtonCallback positiveButtonListener);

    /**
     * <h1>BasicCheckPrompt Dialog</h1>
     * <img width="400" height="221" src="https://github.com/daolq3012/DialogManager/blob/master/image/BasicCheckPrompt.png?raw=true"
     * alt="">
     */
    void dialogBasicCheckPrompt(String title, MaterialDialog.SingleButtonCallback callback);

    /**
     * <h1>Neutral Dialog</h1>
     * <img width="400" height="187" src="https://github.com/daolq3012/DialogManager/blob/master/image/Neutral.png?raw=true"
     * alt="">
     */
    void dialogNeutral(String title, String content, MaterialDialog.SingleButtonCallback callback);

    /**
     * <h1>List Dialog</h1>
     * <img width="400" height="318" src="https://github.com/daolq3012/DialogManager/blob/master/image/List.png?raw=true"
     * alt="">
     */
    void dialogList(String title, @ArrayRes int arrayId, MaterialDialog.ListCallback callback);

    /**
     * <h1>ListWithoutTitle Dialog</h1>
     * <img width="400" height="234" src="https://github.com/daolq3012/DialogManager/blob/master/image/ListWithoutTitle.png?raw=true"
     * alt="">
     */
    void dialogListWithoutTitle(@ArrayRes int arrayId, MaterialDialog.ListCallback callback);

    /**
     * <h1>ListSingleChoice Dialog</h1>
     * <img width="400" height="371" src="https://github.com/daolq3012/DialogManager/blob/master/image/ListSingleChoice.png?raw=true"
     * alt="">
     */
    void dialogListSingleChoice(String title, @ArrayRes int arrayId, int selectedIndex,
            MaterialDialog.ListCallbackSingleChoice callback);

    /**
     * <h1>ListMultiChoice Dialog</h1>
     * <img width="400" height="372" src="https://github.com/daolq3012/DialogManager/blob/master/image/ListMultiChoice.png?raw=true"
     * alt="">
     */
    void dialogListMultiChoice(String title, @ArrayRes int arrayId, Integer[] selectedIndices,
            MaterialDialog.ListCallbackMultiChoice callback);
}
