package com.fstyle.structure_android.utils.navigator;

import android.support.annotation.IntDef;

/**
 * Created by framgia on 03/05/2017.
 */

@IntDef({ ActivityTransition.NONE, ActivityTransition.START, ActivityTransition.FINISH })
@interface ActivityTransition {
    int NONE = 0x00;
    int START = 0x01;
    int FINISH = 0x02;
}
