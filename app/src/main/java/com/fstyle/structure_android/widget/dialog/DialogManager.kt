package com.fstyle.structure_android.widget.dialog

import android.support.annotation.ArrayRes
import android.support.annotation.DrawableRes
import com.fstyle.library.MaterialDialog

/**
 * Created by le.quang.dao on 14/03/2017.
 */

interface DialogManager {

  /**
   * <h1>Indeterminate Progress Dialog</h1>
   * <img width="400" height="110" src="https://github.com/daolq3012/DialogManager/blob/master/image/IndeterminateProgressDialog.png?raw=true" alt=""></img>
   */
  fun showIndeterminateProgressDialog()

  fun dismissProgressDialog()

  fun dialogError(content: String)

  /**
   * <h1>BasicWithoutTitle Dialog</h1>
   * <img width="400" height="134" src="https://github.com/daolq3012/DialogManager/blob/master/image/BasicWithoutTitle.png?raw=true" alt=""></img>
   */
  fun dialogBasicWithoutTitle(content: String,
      positiveButtonListener: MaterialDialog.SingleButtonCallback)

  /**
   * <h1>Basic Dialog</h1>
   * <img width="400" height="183" src="https://github.com/daolq3012/DialogManager/blob/master/image/Basic.png?raw=true" alt=""></img>
   */
  fun dialogBasic(title: String, content: String,
      positiveButtonListener: MaterialDialog.SingleButtonCallback)

  /**
   * <h1>BasicIcon Dialog</h1>
   * <img width="400" height="211" src="https://github.com/daolq3012/DialogManager/blob/master/image/BasicIcon.png?raw=true" alt=""></img>
   */
  fun dialogBasicIcon(title: String, content: String, @DrawableRes icon: Int,
      positiveButtonListener: MaterialDialog.SingleButtonCallback)

  /**
   * <h1>BasicCheckPrompt Dialog</h1>
   * <img width="400" height="221" src="https://github.com/daolq3012/DialogManager/blob/master/image/BasicCheckPrompt.png?raw=true" alt=""></img>
   */
  fun dialogBasicCheckPrompt(title: String, callback: MaterialDialog.SingleButtonCallback)

  /**
   * <h1>Neutral Dialog</h1>
   * <img width="400" height="187" src="https://github.com/daolq3012/DialogManager/blob/master/image/Neutral.png?raw=true" alt=""></img>
   */
  fun dialogNeutral(title: String, content: String, callback: MaterialDialog.SingleButtonCallback)

  /**
   * <h1>List Dialog</h1>
   * <img width="400" height="318" src="https://github.com/daolq3012/DialogManager/blob/master/image/List.png?raw=true" alt=""></img>
   */
  fun dialogList(title: String, @ArrayRes arrayId: Int, callback: MaterialDialog.ListCallback)

  /**
   * <h1>ListWithoutTitle Dialog</h1>
   * <img width="400" height="234" src="https://github.com/daolq3012/DialogManager/blob/master/image/ListWithoutTitle.png?raw=true" alt=""></img>
   */
  fun dialogListWithoutTitle(@ArrayRes arrayId: Int, callback: MaterialDialog.ListCallback)

  /**
   * <h1>ListSingleChoice Dialog</h1>
   * <img width="400" height="371" src="https://github.com/daolq3012/DialogManager/blob/master/image/ListSingleChoice.png?raw=true" alt=""></img>
   */
  fun dialogListSingleChoice(title: String, @ArrayRes arrayId: Int, selectedIndex: Int,
      callback: MaterialDialog.ListCallbackSingleChoice)

  /**
   * <h1>ListMultiChoice Dialog</h1>
   * <img width="400" height="372" src="https://github.com/daolq3012/DialogManager/blob/master/image/ListMultiChoice.png?raw=true" alt=""></img>
   */
  fun dialogListMultiChoice(title: String, @ArrayRes arrayId: Int, selectedIndices: Array<Int>,
      callback: MaterialDialog.ListCallbackMultiChoice)
}
