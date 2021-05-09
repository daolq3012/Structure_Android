package com.ccc.nameapp.data.source.remote.api.error

import android.content.Context
import retrofit2.Response

class BaseException : RuntimeException {

    private val errorType: String
    private lateinit var response: Response<*>
    private var errorResponse: ErrorResponse? = null

    private constructor(type: String, cause: Throwable) : super(cause.message, cause) {
        this.errorType = type
    }

    private constructor(type: String, response: Response<*>) {
        this.errorType = type
        this.response = response
    }

    constructor(type: String, response: ErrorResponse?) {
        this.errorType = type
        this.errorResponse = response
    }

    fun getMessageError(context: Context? = null): String {
        when (errorType) {
            Type.SERVER -> {
                errorResponse?.message?.let { errorMessage ->
                    return errorMessage
                }
            }
            Type.NETWORK -> {
                return getNetworkErrorMessage(cause)
            }
            Type.HTTP -> {
                return response.code().getHttpErrorMessage()
            }
        }
        return "Error"
    }

    private fun getNetworkErrorMessage(throwable: Throwable?): String {
        return throwable?.message.toString()
    }

    private fun Int.getHttpErrorMessage(): String {
        if (this in 300..308) {
            // Redirection
            return "It was transferred to a different URL. I'm sorry for causing you trouble"
        }
        if (this in 400..451) {
            // Client error
            return "An error occurred on the application side. Please try again later!"
        }
        if (this in 500..511) {
            // Server error
            return "A server error occurred. Please try again later!"
        }

        // Unofficial error
        return "An error occurred. Please try again later!"
    }

    companion object {
        fun toNetworkError(cause: Throwable): BaseException {
            return BaseException(Type.NETWORK, cause)
        }

        fun toHttpError(response: Response<*>): BaseException {
            return BaseException(Type.HTTP, response)
        }

        fun toUnexpectedError(cause: Throwable): BaseException {
            return BaseException(Type.UNEXPECTED, cause)
        }

        fun toServerError(response: ErrorResponse): BaseException {
            return BaseException(Type.SERVER, response)
        }
    }
}
