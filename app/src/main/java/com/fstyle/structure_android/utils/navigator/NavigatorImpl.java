package com.fstyle.structure_android.utils.navigator;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Patterns;

import com.fstyle.structure_android.screen.BaseView;

/**
 * Created by le.quang.dao on 14/03/2017.
 */

public class NavigatorImpl implements Navigator {

    @NonNull
    private Activity mActivity;

    public NavigatorImpl(@NonNull Activity activity) {
        mActivity = activity;
    }

    public <V extends BaseView> NavigatorImpl(@NonNull V view) {
        if (view instanceof Activity) {
            mActivity = (Activity) view;
        }
    }

    @Override
    public void startActivity(@NonNull Intent intent) {
        mActivity.startActivity(intent);
    }

    @Override
    public void startActivity(@NonNull Class<? extends Activity> clazz) {
        mActivity.startActivity(new Intent(mActivity, clazz));
    }

    @Override
    public void startActivity(@NonNull Class<? extends Activity> clazz, Bundle args) {
        Intent intent = new Intent(mActivity, clazz);
        intent.putExtras(args);
        startActivity(intent);
    }

    @Override
    public void startActivityAtRoot(@NonNull Class<? extends Activity> clazz) {
        Intent intent = new Intent(mActivity, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void startActivityForResult(@NonNull Intent intent, int requestCode) {
        mActivity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void startActivityForResult(@NonNull Class<? extends Activity> clazz, Bundle args,
            int requestCode) {
        Intent intent = new Intent(mActivity, clazz);
        intent.putExtras(args);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void finishActivityWithResult(Intent intent, int resultCode) {
        mActivity.setResult(resultCode, intent);
        mActivity.finish();
    }

    @Override
    public void openUrl(String url) {
        if (TextUtils.isEmpty(url) || !Patterns.WEB_URL.matcher(url).matches()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
        mActivity.startActivity(intent);
    }
}
