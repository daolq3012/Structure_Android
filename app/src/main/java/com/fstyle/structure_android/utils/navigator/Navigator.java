package com.fstyle.structure_android.utils.navigator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by le.quang.dao on 17/03/2017.
 */

public interface Navigator {

    void startActivity(@NonNull Intent intent);

    void startActivity(@NonNull Class<? extends Activity> clazz);

    void startActivity(@NonNull Class<? extends Activity> clazz, Bundle args);

    void startActivityAtRoot(@NonNull Class<? extends Activity> clazz);

    void startActivityForResult(@NonNull Intent intent, int requestCode);

    void startActivityForResult(@NonNull Class<? extends Activity> clazz, Bundle args,
            int requestCode);

    void finishActivityWithResult(Intent intent, int resultCode);

    void openUrl(String url);
}
