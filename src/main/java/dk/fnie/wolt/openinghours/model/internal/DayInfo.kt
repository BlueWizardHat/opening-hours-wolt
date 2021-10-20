package dk.fnie.wolt.openinghours.model.internal

import dk.fnie.wolt.openinghours.model.shared.DayName

data class DayInfo(
    val day: DayName,
    val openingHours: List<OpeningRange>
)
