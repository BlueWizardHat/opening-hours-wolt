package dk.fnie.wolt.openinghours.formatter

import dk.fnie.wolt.openinghours.formatter.OpeningHoursFormatter.formatted
import dk.fnie.wolt.openinghours.model.internal.DayInfo
import dk.fnie.wolt.openinghours.model.internal.OpeningRange
import dk.fnie.wolt.openinghours.model.shared.DayName.monday
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.LocalTime
import kotlin.test.assertEquals

class OpeningHoursFormatterTest {
    @ParameterizedTest
    @MethodSource(value = ["formattedArguments"])
    fun formatted(days: List<DayInfo>, expectedResult: String) =
        assertEquals(expectedResult, days.formatted())

    companion object {
        @JvmStatic
        fun formattedArguments() =
            listOf(
                // Closed
                Arguments.of(
                    listOf(DayInfo(monday, listOf())),
                    "Monday: Closed\n"
                ),
                // Single Open range
                Arguments.of(
                    listOf(DayInfo(monday, listOf(openClose(3600, 7200)))),
                    "Monday: 1 AM - 2 AM\n"
                ),
                // Multiple Open Range
                Arguments.of(
                    listOf(DayInfo(monday, listOf(openClose(3600, 7200), openClose(43200, 75600)))),
                    "Monday: 1 AM - 2 AM, 12 PM - 9 PM\n"
                ),
                // 2 digits minutes if not 0
                Arguments.of(
                    listOf(DayInfo(monday, listOf(openClose(3660, 7200)))),
                    "Monday: 1:01 AM - 2 AM\n"
                ),
            )

        private fun openClose(open: Long, close: Long) = OpeningRange(LocalTime.ofSecondOfDay(open), LocalTime.ofSecondOfDay(close))
    }
}
