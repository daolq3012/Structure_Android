package com.fstyle.structure_android.utils.navigator;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Patterns;
import com.fstyle.structure_android.R;

/**
 * Created by le.quang.dao on 17/03/2017.
 */

public class Navigator {

    @NonNull
    private Activity mActivity;
    @NonNull
    private Fragment mFragment;

    public Navigator(@NonNull Activity activity) {
        mActivity = activity;
    }

    public Navigator(@NonNull Fragment fragment) {
        mFragment = fragment;
        mActivity = fragment.getActivity();
    }

    private void startActivity(@NonNull Intent intent) {
        mActivity.startActivity(intent);
        setActivityTransactionAnimation(ActivityTransition.START);
    }

    public void startActivity(@NonNull Class<? extends Activity> clazz) {
        Intent intent = new Intent(mActivity, clazz);
        startActivity(intent);
    }

    public void startActivity(@NonNull Class<? extends Activity> clazz, Bundle args) {
        Intent intent = new Intent(mActivity, clazz);
        intent.putExtras(args);
        startActivity(intent);
    }

    public void startActivityAtRoot(@NonNull Class<? extends Activity> clazz) {
        Intent intent = new Intent(mActivity, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void startActivityForResult(@NonNull Intent intent, int requestCode) {
        mActivity.startActivityForResult(intent, requestCode);
        setActivityTransactionAnimation(ActivityTransition.START);
    }

    public void startActivityForResult(@NonNull Class<? extends Activity> clazz, Bundle args,
            int requestCode) {
        Intent intent = new Intent(mActivity, clazz);
        intent.putExtras(args);
        startActivityForResult(intent, requestCode);
    }

    public void finishActivity() {
        mActivity.finish();
        setActivityTransactionAnimation(ActivityTransition.FINISH);
    }

    public void finishActivityWithResult(Intent intent, int resultCode) {
        mActivity.setResult(resultCode, intent);
        finishActivity();
    }

    public void openUrl(String url) {
        if (TextUtils.isEmpty(url) || !Patterns.WEB_URL.matcher(url).matches()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
        startActivity(intent);
    }

    // Fragment

    /**
     * Go to next fragment which nested inside current fragment
     *
     * @param fragment new child fragment
     */
    public void goNextChildFragment(@IdRes int containerViewId, Fragment fragment,
            boolean addToBackStack, int animation, String tag) {
        FragmentTransaction transaction = mFragment.getChildFragmentManager().beginTransaction();
        setFragmentTransactionAnimation(transaction, animation);
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        transaction.replace(containerViewId, fragment, tag);
        transaction.commitAllowingStateLoss();
        mFragment.getChildFragmentManager().executePendingTransactions();
    }

    /**
     * Always keep 1 fragment in container
     *
     * @return true if fragment stack has pop
     */
    public boolean goBackChildFragment() {
        boolean isShowPrevious = mFragment.getChildFragmentManager().getBackStackEntryCount() > 1;
        if (isShowPrevious) {
            mFragment.getChildFragmentManager().popBackStackImmediate();
        }
        return isShowPrevious;
    }

    private void setFragmentTransactionAnimation(FragmentTransaction transaction,
            @NavigateAnim int animation) {
        switch (animation) {
            case NavigateAnim.FADED:
                transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                        android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case NavigateAnim.RIGHT_LEFT:
                transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                        R.anim.slide_left_in, R.anim.slide_right_out);
                break;
            case NavigateAnim.LEFT_RIGHT:
                transaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_right_out,
                        R.anim.slide_right_in, R.anim.slide_left_out);
                break;
            case NavigateAnim.BOTTOM_UP:
                transaction.setCustomAnimations(R.anim.slide_bottom_in, R.anim.slide_top_out,
                        R.anim.slide_top_in, R.anim.slide_bottom_out);
                break;
            case NavigateAnim.NONE:
                break;
            default:
                break;
        }
    }

    private void setActivityTransactionAnimation(@ActivityTransition int animation) {
        switch (animation) {
            case ActivityTransition.START:
                mActivity.overridePendingTransition(R.anim.translate_left, R.anim.translate_still);
                break;
            case ActivityTransition.FINISH:
                mActivity.overridePendingTransition(R.anim.translate_still, R.anim.translate_right);
                break;
            case ActivityTransition.NONE:
                break;
            default:
                break;
        }
    }
}
