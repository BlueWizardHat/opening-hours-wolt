package dk.fnie.wolt.openinghours.converter

import dk.fnie.wolt.openinghours.converter.OpeningHoursConverter.toDayInfo
import dk.fnie.wolt.openinghours.model.external.TimeData
import dk.fnie.wolt.openinghours.model.external.WeekData
import dk.fnie.wolt.openinghours.model.internal.DayInfo
import dk.fnie.wolt.openinghours.model.internal.OpeningRange
import dk.fnie.wolt.openinghours.model.shared.DayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.LocalTime
import kotlin.test.assertEquals

class OpeningHoursConverterTest {
    @ParameterizedTest
    @MethodSource(value = ["getDayInfoArguments"])
    fun toDayInfo(weekData: WeekData, expectedResult: List<DayInfo>) =
        assertEquals(expectedResult, weekData.toDayInfo())

    companion object {
        @JvmStatic
        fun getDayInfoArguments() =
            listOf(
                // Simple open close same day
                Arguments.of(
                    WeekData(monday = listOf(open(3600), close(7200))),
                    dayInfo(monday = listOf(openClose(3600, 7200)))
                ),
                // Open close multiple same day
                Arguments.of(
                    WeekData(monday = listOf(open(3600), close(7200), open(36000), close(37000))),
                    dayInfo(monday = listOf(openClose(3600, 7200), openClose(36000, 37000)))
                ),
                // Leading close for a day is ignored for that day since it probably belong to the previous day
                Arguments.of(
                    WeekData(monday = listOf(close(1200), open(3600), close(7200))),
                    dayInfo(monday = listOf(openClose(3600, 7200)))
                ),
                // Open monday, close tuesday -> concatenated on monday
                Arguments.of(
                    WeekData(monday = listOf(open(36000)), tuesday = listOf(close(3600))),
                    dayInfo(monday = listOf(openClose(36000, 3600)))
                ),
                // Close is missing, next day start with open -> assume close is same as open
                Arguments.of(
                    WeekData(monday = listOf(open(36000)), tuesday = listOf(open(3600), close(7200))),
                    dayInfo(monday = listOf(openClose(36000, 3600)), tuesday = listOf(openClose(3600, 7200)))
                ),
                // Close is missing completely -> assume close is midnight
                Arguments.of(
                    WeekData(monday = listOf(open(3600))),
                    dayInfo(monday = listOf(openClose(3600, 0)))
                )
            )

        private fun open(secondOfDay: Long): TimeData = TimeData("open", secondOfDay)
        private fun close(secondOfDay: Long): TimeData = TimeData("close", secondOfDay)
        private fun openClose(open: Long, close: Long) = OpeningRange(LocalTime.ofSecondOfDay(open), LocalTime.ofSecondOfDay(close))

        private fun dayInfo(
            monday: List<OpeningRange> = emptyList(),
            tuesday: List<OpeningRange> = emptyList(),
            wednesday: List<OpeningRange> = emptyList(),
            thursday: List<OpeningRange> = emptyList(),
            friday: List<OpeningRange> = emptyList(),
            saturday: List<OpeningRange> = emptyList(),
            sunday: List<OpeningRange> = emptyList(),
        ) = listOf(
            DayInfo(DayName.monday, monday),
            DayInfo(DayName.tuesday, tuesday),
            DayInfo(DayName.wednesday, wednesday),
            DayInfo(DayName.thursday, thursday),
            DayInfo(DayName.friday, friday),
            DayInfo(DayName.saturday, saturday),
            DayInfo(DayName.sunday, sunday),
        )
    }
}
