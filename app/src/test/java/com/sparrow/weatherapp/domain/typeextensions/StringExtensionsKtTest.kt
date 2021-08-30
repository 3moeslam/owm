package com.sparrow.weatherapp.domain.typeextensions

import org.junit.Assert.*
import org.junit.Test

class IsZipCodeTest {

    @Test
    fun `Given valid zip (cairo - future city) code Then true`(){
        val result = "11831".isValidZipCode()
        assertTrue(
            "When pass valid zip (cairo - future city) to isValidZipCode extension it should return true - Make sure you use valid regex in [StringExtensionsKt#isValidZipCode]",
            result
        )
    }

    @Test
    fun `Given valid zip (Washington, DC) code Then true`(){
        val result = "20001".isValidZipCode()
        assertTrue("When valid zip (Washington, DC) to isValidZipCode extension it should return true - Make sure you use valid regex in [StringExtensionsKt#isValidZipCode]", result)
    }

    @Test
    fun `Given empty string Then false`(){
        val result = "".isValidZipCode()
        assertFalse("When pass empty string to isValidZipCode extension it should return false - Make sure you use valid regex in [StringExtensionsKt#isValidZipCode]", result)
    }

    @Test
    fun `Given any string (invalid zip code) Then false`(){
        val result = "".isValidZipCode()
        assertFalse("When pass any string (invalid zip code) to isValidZipCode extension it should return false - Make sure you use valid regex in [StringExtensionsKt#isValidZipCode]", result)
    }
}