package dk.fnie.wolt.openinghours.service

import com.fasterxml.jackson.databind.ObjectMapper
import dk.fnie.wolt.openinghours.model.external.WeekData
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class OpeningHoursServiceTest {
    @Test
    fun formatOpeningHours() {
        // Setup
        val weekData = ObjectMapper().readValue(this.javaClass.getResource("/testdata.json"), WeekData::class.java)
        val expectedResult = """
            Monday: Closed
            Tuesday: 10 AM - 6 PM
            Wednesday: Closed
            Thursday: 10:30 AM - 6 PM
            Friday: 10 AM - 1 AM
            Saturday: 10 AM - 1 AM
            Sunday: 12 PM - 9 PM
        """.trimIndent() + "\n"

        // Execute
        val actualResult = OpeningHoursService().formatOpeningHours(weekData)

        // Verify
        assertEquals(expectedResult, actualResult)
    }
}
