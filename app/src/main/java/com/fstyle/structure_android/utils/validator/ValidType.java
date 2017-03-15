package com.fstyle.structure_android.utils.validator;

import android.support.annotation.IntDef;

/**
 * Created by le.quang.dao on 17/03/2017.
 */

@IntDef({ ValidType.NON_EMPTY, ValidType.NG_WORD, ValidType.VALUE_RANGE_0_100 })
public @interface ValidType {
    int NON_EMPTY = 0x00;
    int NG_WORD = 0x01;
    int VALUE_RANGE_0_100 = 0x02;
}
