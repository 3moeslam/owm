package com.sparrow.weatherapp.domain.typeextensions

import com.sparrow.weatherapp.entities.CallResult
import com.sparrow.weatherapp.entities.Coord
import com.sparrow.weatherapp.entities.LatLon
import com.sparrow.weatherapp.entities.LocationByNameResponseItem
import com.sparrow.weatherapp.entities.ZipCodeLocationResponse
import org.junit.Assert.*
import org.junit.Test

class LatLonExtensionsKtTest {

    @Test
    fun `Given CallResult#Success#LocationByNameResponse And valid response Then return LatLon`() {
        val response = listOf(LocationByNameResponseItem(lat = 1.0, lon = 1.0))
        val callResult = CallResult.Success(response)

        val result = callResult.toLatLon()

        assertEquals(LatLon(1.0, 1.0), result)
    }

    @Test
    fun `Given CallResult#Success#LocationByNameResponse And in-valid response Then return null`() {
        val response = listOf(LocationByNameResponseItem())
        val callResult = CallResult.Success(response)

        val result = callResult.toLatLon()

        assertNull(result)
    }

    @Test
    fun `Given CallResult#Success#String Then return null`() {
        val callResult = CallResult.Success("")

        val result = callResult.toLatLon()

        assertNull(result)
    }

    @Test
    fun `Given CallResult#Success#ZipCodeLocationResponse And in-valid response Then return null`() {
        val response = ZipCodeLocationResponse()
        val callResult = CallResult.Success(response)

        val result = callResult.toLatLon()

        assertNull(result)
    }

    @Test
    fun `Given CallResult#Success#ZipCodeLocationResponse And valid response Then return LatLon`() {
        val response = ZipCodeLocationResponse(Coord(1.0, 1.0))
        val callResult = CallResult.Success(response)

        val result = callResult.toLatLon()

        assertEquals(LatLon(1.0, 1.0), result)
    }
}