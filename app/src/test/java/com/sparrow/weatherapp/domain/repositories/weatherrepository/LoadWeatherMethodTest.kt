package com.sparrow.weatherapp.domain.repositories.weatherrepository

import com.sparrow.weatherapp.domain.datasources.GeoLocationService
import com.sparrow.weatherapp.domain.datasources.WeatherService
import com.sparrow.weatherapp.domain.repositories.WeatherRepository
import com.sparrow.weatherapp.entities.CallResult
import com.sparrow.weatherapp.entities.Coord
import com.sparrow.weatherapp.entities.OneCallResponse
import com.sparrow.weatherapp.entities.ZipCodeLocationResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.serialization.json.Json
import org.junit.Test
import retrofit2.Response

/**
 * Test cases for [WeatherRepository.loadWeatherData] method
 * this tests will not cover location loading logic as it covered in [LoadLocationTest]
 *
 * @note: All tests depends on stubbed response exist in resources folder
 *
 * @author Eslam Ahmad
 */
class LoadWeatherMethodTest {

    @Test
    fun `Given location Not loaded When loadWeather Then return CallResult#Error`() = runBlockingTest {
        val searchKey = "20001"
        val response = mockk<Response<ZipCodeLocationResponse>> {
            every { code() }.returns(500)
            every { isSuccessful }.returns(false)
        }
        val locationService = mockk<GeoLocationService> {
            coEvery { loadLocationByZipCode(searchKey) }.returns(response)
        }
        val weatherService = mockk<WeatherService>()
        val repo = WeatherRepository(locationService, weatherService)

        val result = repo.loadWeatherData(searchKey)

        assert(result is CallResult.Error)
    }

    @Test
    fun `Given loadWeather When locationLoaded success Then hit WeatherService#loadWeather`() = runBlockingTest {
        val searchKey = "20001"
        val oneCallResponseText = javaClass.getResource("/one-call-response.json").readText()
        val oneCallResponse = Json { ignoreUnknownKeys = true }.decodeFromString(OneCallResponse.serializer(), oneCallResponseText)
        val locationService = mockk<GeoLocationService> {
            coEvery { loadLocationByZipCode(searchKey) }.returns(Response.success(ZipCodeLocationResponse(Coord(1.0, 1.0))))
        }
        val weatherService = mockk<WeatherService> {
            coEvery { loadWeather(any(), any()) }.returns(Response.success(oneCallResponse))
        }
        val repo = WeatherRepository(locationService, weatherService)

        repo.loadWeatherData(searchKey)

        coVerify {
            weatherService.loadWeather(any(), any())
        }
    }


    @Test
    fun `Given repo#loadWeather When service#loadWeather success Then return CallResult#Success`() = runBlockingTest {
        val searchKey = "20001"
        val oneCallResponseText = javaClass.getResource("/one-call-response.json").readText()
        val oneCallResponse = Json { ignoreUnknownKeys = true }.decodeFromString(OneCallResponse.serializer(), oneCallResponseText)
        val locationService = mockk<GeoLocationService> {
            coEvery { loadLocationByZipCode(searchKey) }.returns(Response.success(ZipCodeLocationResponse(Coord(1.0, 1.0))))
        }
        val weatherService = mockk<WeatherService> {
            coEvery { loadWeather(any(), any()) }.returns(Response.success(oneCallResponse))
        }
        val repo = WeatherRepository(locationService, weatherService)

        val result = repo.loadWeatherData(searchKey)

        assert(result is CallResult.Success<*>)
    }
}