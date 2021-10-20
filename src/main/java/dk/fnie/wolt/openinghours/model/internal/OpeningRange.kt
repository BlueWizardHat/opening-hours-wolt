package dk.fnie.wolt.openinghours.model.internal

import java.time.LocalTime

data class OpeningRange(
    val open: LocalTime,
    val close: LocalTime
)
