package com.sparrow.weatherapp.domain.repositories.weatherrepository

import com.sparrow.weatherapp.domain.ErrorCodes
import com.sparrow.weatherapp.domain.datasources.GeoLocationService
import com.sparrow.weatherapp.domain.datasources.WeatherService
import com.sparrow.weatherapp.domain.repositories.WeatherRepository
import com.sparrow.weatherapp.entities.CallResult
import com.sparrow.weatherapp.entities.ErrorType
import com.sparrow.weatherapp.entities.ZipCodeLocationResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Response

class LoadLocationTest {

    @Test
    fun `Given empty string Then show error`() = runBlockingTest {
        val geoLocationService = mockk<GeoLocationService>()
        val weatherService = mockk<WeatherService>()
        val repo = WeatherRepository(geoLocationService, weatherService)

        val result = repo.loadLocation("")
        val error = result as? CallResult.Error
        val errorType = error?.errorType
        val logicalError = errorType as? ErrorType.LogicalError

        assertEquals(ErrorCodes.INVALID_DATA, logicalError?.errorCode)
    }

    @Test
    fun `Given valid zip code Then hit GeoLocationService#loadLocationByZipCode`() = runBlockingTest {
        val geoLocationService = mockk<GeoLocationService> {
            coEvery { loadLocationByZipCode(any()) }.returns(Response.success(ZipCodeLocationResponse(null)))
        }
        val weatherService = mockk<WeatherService>()

        val repo = WeatherRepository(geoLocationService, weatherService)

        repo.loadLocation("20001")

        coVerify {
            geoLocationService.loadLocationByZipCode("20001")
        }
    }

    @Test
    fun `Given invalid zip code Then hit GeoLocationService#loadLocationByName`() = runBlockingTest {
        val location = "2000x"
        val geoLocationService = mockk<GeoLocationService> {
            coEvery { loadLocationByName(location) }.returns(Response.success(listOf()))
        }
        val weatherService = mockk<WeatherService>()

        val repo = WeatherRepository(geoLocationService, weatherService)

        repo.loadLocation(location)

        coVerify {
            geoLocationService.loadLocationByName(location)
        }
    }


    @Test
    fun `Given any string Then hit GeoLocationService#loadLocationByName`() = runBlockingTest {
        val location = "Cairo"
        val geoLocationService = mockk<GeoLocationService> {
            coEvery { loadLocationByName(location) }.returns(Response.success(listOf()))
        }
        val weatherService = mockk<WeatherService>()
        val repo = WeatherRepository(geoLocationService, weatherService)

        repo.loadLocation(location)

        coVerify {
            geoLocationService.loadLocationByName(location)
        }
    }
}