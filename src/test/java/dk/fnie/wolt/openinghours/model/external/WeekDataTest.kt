package dk.fnie.wolt.openinghours.model.external

import dk.fnie.wolt.openinghours.model.shared.DayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertSame

class WeekDataTest {
    @ParameterizedTest
    @MethodSource(value = ["getDayByNameArguments"])
    fun getDayByName(day: DayName, expectedResult: List<TimeData>) =
        assertSame(expectedResult, testData.getDayByName(day))

    companion object {
        private val monday = listOf(TimeData("open", 3600))
        private val tuesday = listOf(TimeData("open", 3601))
        private val wednesday = listOf(TimeData("open", 3602))
        private val thursday = listOf(TimeData("open", 3603))
        private val friday = listOf(TimeData("open", 3604))
        private val saturday = listOf(TimeData("open", 3605))
        private val sunday = listOf(TimeData("open", 3606))
        private val testData = WeekData(monday, tuesday, wednesday, thursday, friday, saturday, sunday)

        @JvmStatic
        fun getDayByNameArguments(): List<Arguments> =
            listOf(
                Arguments.of(DayName.monday, monday),
                Arguments.of(DayName.tuesday, tuesday),
                Arguments.of(DayName.wednesday, wednesday),
                Arguments.of(DayName.thursday, thursday),
                Arguments.of(DayName.friday, friday),
                Arguments.of(DayName.saturday, saturday),
                Arguments.of(DayName.sunday, sunday),
            )
    }
}
