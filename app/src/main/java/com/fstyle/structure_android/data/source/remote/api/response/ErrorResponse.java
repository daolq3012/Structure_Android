package com.fstyle.structure_android.data.source.remote.api.response;

import com.google.gson.annotations.Expose;

/**
 * Created by framgia on 11/05/2017.
 */

public class ErrorResponse {
    @Expose
    private String documentationUrl;
    @Expose
    private String message;

    public String getDocumentationUrl() {
        return documentationUrl;
    }

    public String getMessage() {
        return message;
    }
}
