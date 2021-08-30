package com.sparrow.weatherapp.domain.typeextensions

import com.sparrow.weatherapp.domain.ErrorCodes
import com.sparrow.weatherapp.entities.CallResult
import com.sparrow.weatherapp.entities.ErrorType
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

class ToCallResultFunctionTest {

    @Test
    fun `Given success then return CallResult#Success`() {
        val response = Response.success("")

        val result = response.toCallResult()

        assert(result is CallResult.Success<*>)
    }

    @Test
    fun `Given serverError 500 Result then return CallResult#Error#ApiError with code SERVER_ERROR`() {
        val response = mockk<Response<*>> {
            every { isSuccessful }.returns(false)
            every { code() }.returns(500)
        }

        val result = response.toCallResult()
        val error = result as? CallResult.Error
        val errorType = error?.errorType as? ErrorType.ApiError

        assertEquals(ErrorCodes.SERVER_ERROR, errorType?.errorCode)
    }

    @Test
    fun `Given serverSuccess 200 and null body Then return CallResult#Error#LogicalError`() {
        val response = mockk<Response<*>> {
            every { isSuccessful }.returns(true)
            every { code() }.returns(200)
            every { body() }.returns(null)
            every { errorBody() }.returns(null)
        }

        val result = response.toCallResult()
        val error = result as? CallResult.Error
        val errorType = error?.errorType as? ErrorType.LogicalError

        assert(errorType is ErrorType.LogicalError)
    }

    @Test
    fun `Given response code 300 Then return CallResult#Error#UnknownError`() {
        val response = mockk<Response<*>> {
            every { isSuccessful }.returns(false)
            every { code() }.returns(300)
            every { body() }.returns("")
            every { errorBody() }.returns(null)
        }

        val result = response.toCallResult()
        val error = result as? CallResult.Error
        val errorType = error?.errorType as? ErrorType.UnknownError

        assert(errorType is ErrorType.UnknownError)
    }
}