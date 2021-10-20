package dk.fnie.wolt.openinghours.model.external

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalTime

data class TimeData(
    /**
     * "open" or "close".
     */
    @JsonProperty("type")
    val type: String,

    /**
     * Seconds from midnight.
     */
    @JsonProperty("value")
    val secondOfDay: Long
) {
    fun timeOfDay(): LocalTime = LocalTime.ofSecondOfDay(secondOfDay)
}
