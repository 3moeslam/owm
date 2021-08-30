package com.sparrow.weatherapp.entities

import com.sparrow.weatherapp.domain.ErrorCodes
import retrofit2.Response

/**
 * General call result,
 * should be used in future with call adapter to replace [Response]
 *
 * @author Eslam Ahmad
 */
sealed class CallResult {
    /**
     * Data error state, which hold the error type to be handled by presentation layer
     *
     * @author Eslam Ahmad
     */
    class Error(val errorType: ErrorType) : CallResult()

    /**
     * Data success state, which hold data
     */
    class Success<out T>(val data: T) : CallResult()


    /**
     * Directly return data from call, this method will reduce boilerplate code to get data
     *
     * @author Eslam Ahmad
     */
    fun <T> data(): T? = (this as? Success<T>)?.data

    companion object {


        /**
         * Factory method to produce [Error] with [ErrorType.LogicalError] with code [ErrorCodes.INVALID_DATA]
         *
         * @author Eslam Ahmad
         */
        fun invalidDataError() = Error(ErrorType.LogicalError(ErrorCodes.INVALID_DATA))
    }
}

/**
 * Error general types, to be used to show and handle errors
 *
 * @author Eslam Ahmad
 */
sealed class ErrorType {
    object NoInternet : ErrorType()

    class UnknownError(val errorCode: String?) : ErrorType()
    class ApiError(val errorCode: String?) : ErrorType()
    class LogicalError(val errorCode: String?) : ErrorType()
}