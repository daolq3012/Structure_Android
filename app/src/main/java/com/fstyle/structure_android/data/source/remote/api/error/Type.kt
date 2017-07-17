package com.fstyle.structure_android.data.source.remote.api.error

import java.io.IOException

/**
 * Error type
 */
object Type {
  /**
   * An [IOException] occurred while communicating to the server.
   */
  const val NETWORK = "NETWORK"

  /**
   * A non-2xx HTTP status code was received from the server.
   */
  const val HTTP = "HTTP"

  /**
   * A error server with code & message
   */
  const val SERVER = "SERVER"

  /**
   * An internal error occurred while attempting to execute a request. It is best practice to
   * re-throw this exception so your application crashes.
   */
  const val UNEXPECTED = "UNEXPECTED"
}
