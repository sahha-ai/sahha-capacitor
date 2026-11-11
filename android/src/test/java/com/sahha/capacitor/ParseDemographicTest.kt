package com.sahha.capacitor

import ai.sahha.api.demographic.SahhaDemographic
import com.getcapacitor.JSObject
import com.getcapacitor.PluginCall
import org.junit.Assert.assertEquals
import org.junit.Test

class ParseDemographicTest {

    // The demographic as postDemographic receives it: the bridge parses the call's options from
    // JSON text, and PluginCall.getObject copies the nested object, JSON nulls included.
    private fun demographic(json: String): JSObject {
        val options = JSObject("""{"demographic":$json}""")
        val call = PluginCall(null, "Sahha", PluginCall.CALLBACK_ID_DANGLING, "postDemographic", options)
        return call.getObject("demographic")!!
    }

    @Test
    fun `gender and birth date set are kept`() {
        val sahhaDemographic = parseDemographic(demographic("""{"gender":"Female","birthDate":"1990-01-31"}"""))

        assertEquals(SahhaDemographic(gender = "Female", birthDate = "1990-01-31"), sahhaDemographic)
    }

    @Test
    fun `fields left out are unset`() {
        assertEquals(SahhaDemographic(), parseDemographic(demographic("{}")))
    }

    @Test
    fun `JSON nulls are unset rather than the string null`() {
        val sahhaDemographic = parseDemographic(demographic("""{"gender":null,"birthDate":null}"""))

        // Checked field by field, because SahhaDemographic prints null and "null" the same way.
        assertEquals(null, sahhaDemographic.gender)
        assertEquals(null, sahhaDemographic.birthDate)
    }

    @Test
    fun `empty strings are unset`() {
        assertEquals(SahhaDemographic(), parseDemographic(demographic("""{"gender":"","birthDate":""}""")))
    }
}
