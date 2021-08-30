package com.sparrow.weatherapp.domain.typeextensions

import com.sparrow.weatherapp.domain.ErrorCodes
import com.sparrow.weatherapp.entities.CallResult
import com.sparrow.weatherapp.entities.ErrorType
import retrofit2.Response

/**
 * Map retrofit [Response] object to our own type to isolate app from future changes in Retrofit,
 * this is not a best approach we can create our call adapter, but it isolates for now!
 *
 * @author Eslam Ahmad
 */
fun <T> Response<T>.toCallResult(): CallResult {
    return if (isSuccessful && body() != null) {
        CallResult.Success(body())
    } else {
        CallResult.Error(getErrorType())
    }
}

private fun <T> Response<T>.getErrorType(): ErrorType = when {
    code().isServerError -> ErrorType.ApiError(ErrorCodes.SERVER_ERROR)
    code().isRequestError -> ErrorType.ApiError(ErrorCodes.REQUEST_ERROR)
    isSuccessful && body() == null -> ErrorType.LogicalError(errorBody()?.string())
    else -> ErrorType.UnknownError(errorBody()?.string())
}

private val Int.isServerError: Boolean get() = this in 500..599
private val Int.isRequestError: Boolean get() = this in 400..499
