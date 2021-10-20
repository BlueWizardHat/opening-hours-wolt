package dk.fnie.wolt.openinghours.model.external

import dk.fnie.wolt.openinghours.model.shared.DayName

data class WeekData(
    val monday: List<TimeData> = emptyList(),
    val tuesday: List<TimeData> = emptyList(),
    val wednesday: List<TimeData> = emptyList(),
    val thursday: List<TimeData> = emptyList(),
    val friday: List<TimeData> = emptyList(),
    val saturday: List<TimeData> = emptyList(),
    val sunday: List<TimeData> = emptyList()
) {
    fun getDayByName(name: DayName): List<TimeData> = when (name) {
        DayName.monday -> monday
        DayName.tuesday -> tuesday
        DayName.wednesday -> wednesday
        DayName.thursday -> thursday
        DayName.friday -> friday
        DayName.saturday -> saturday
        DayName.sunday -> sunday
    }
}
