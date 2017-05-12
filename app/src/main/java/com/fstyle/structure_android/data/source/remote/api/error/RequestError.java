package com.fstyle.structure_android.data.source.remote.api.error;

import rx.functions.Action1;

/**
 * Created by Sun on 4/16/2017.
 */

public abstract class RequestError implements Action1<Throwable> {

    /**
     * Don't override this method.
     * Override {@link RequestError#onRequestError(BaseException)} instead
     */
    @Override
    public void call(Throwable throwable) {
        if (throwable == null) {
            onRequestError(BaseException.toUnexpectedError(new Throwable("Unknown exception")));
            return;
        }
        if (throwable instanceof BaseException) {
            onRequestError((BaseException) throwable);
        } else {
            onRequestError(BaseException.toUnexpectedError(throwable));
        }
    }

    public abstract void onRequestError(BaseException error);
}
