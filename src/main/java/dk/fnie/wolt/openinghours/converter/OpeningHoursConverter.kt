package dk.fnie.wolt.openinghours.converter

import dk.fnie.wolt.openinghours.model.external.TimeData
import dk.fnie.wolt.openinghours.model.external.WeekData
import dk.fnie.wolt.openinghours.model.internal.DayInfo
import dk.fnie.wolt.openinghours.model.internal.OpeningRange
import dk.fnie.wolt.openinghours.model.shared.DayName
import dk.fnie.wolt.openinghours.model.shared.DayName.friday
import dk.fnie.wolt.openinghours.model.shared.DayName.monday
import dk.fnie.wolt.openinghours.model.shared.DayName.saturday
import dk.fnie.wolt.openinghours.model.shared.DayName.sunday
import dk.fnie.wolt.openinghours.model.shared.DayName.thursday
import dk.fnie.wolt.openinghours.model.shared.DayName.tuesday
import dk.fnie.wolt.openinghours.model.shared.DayName.wednesday
import java.time.LocalTime

/**
 * Converts the received WeekData into a list of DayInfos with any missing closing times for a day resolved.
 */
object OpeningHoursConverter {
    private val nextDayMapping = listOf(
        Pair(monday, tuesday),
        Pair(tuesday, wednesday),
        Pair(wednesday, thursday),
        Pair(thursday, friday),
        Pair(friday, saturday),
        Pair(saturday, sunday),
        Pair(sunday, monday)
    )

    fun WeekData.toDayInfo(): List<DayInfo> =
        nextDayMapping.map { toDayInfo(it.first, it.second, this) }

    private fun toDayInfo(day: DayName, nextDay: DayName, source: WeekData): DayInfo =
        DayInfo(day, toOpeningRanges(day, nextDay, source))

    private fun toOpeningRanges(day: DayName, nextDay: DayName, source: WeekData): List<OpeningRange> {
        val openingRanges = mutableListOf<OpeningRange>()
        var tmpOpen: LocalTime? = null
        source.getDayByName(day).forEach {
            if (tmpOpen == null) {
                if (it.type == "open") {
                    tmpOpen = it.timeOfDay()
                }
            } else {
                if (it.type == "close") {
                    openingRanges.add(OpeningRange(tmpOpen!!, it.timeOfDay()))
                    tmpOpen = null
                }
            }
        }
        if (tmpOpen != null) {
            val firstNextDayTime = source.getDayByName(nextDay).firstOrNull() ?: TimeData("close", 0) // default to midnight
            openingRanges.add(OpeningRange(tmpOpen!!, firstNextDayTime.timeOfDay()))
        }
        return openingRanges
    }
}
