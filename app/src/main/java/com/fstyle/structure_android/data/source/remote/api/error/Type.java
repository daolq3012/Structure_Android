package com.fstyle.structure_android.data.source.remote.api.error;

import android.support.annotation.StringDef;

import java.io.IOException;

/**
 * Error type
 */
@StringDef({Type.NETWORK, Type.HTTP, Type.UNEXPECTED, Type.SERVER})
@interface Type {

    /**
     * An {@link IOException} occurred while communicating to the server.
     */
    String NETWORK = "NETWORK";

    /**
     * A non-2xx HTTP status code was received from the server.
     */
    String HTTP = "HTTP";

    /**
     * A error server with code & message
     */
    String SERVER = "SERVER";

    /**
     * An internal error occurred while attempting to execute a request. It is best practice to
     * re-throw this exception so your application crashes.
     */
    String UNEXPECTED = "UNEXPECTED";
}
