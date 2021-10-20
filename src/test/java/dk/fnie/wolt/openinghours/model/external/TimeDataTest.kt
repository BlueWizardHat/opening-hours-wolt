package dk.fnie.wolt.openinghours.model.external

import java.time.LocalTime
import kotlin.test.Test
import kotlin.test.assertEquals

class TimeDataTest {
    @Test
    fun timeOfDay() {
        // Setup
        val input = TimeData("test", 3600)
        val expectedResult = LocalTime.ofSecondOfDay(3600)

        // Execute
        val actualResult = input.timeOfDay()

        // Verify
        assertEquals(expectedResult, actualResult)
    }
}
