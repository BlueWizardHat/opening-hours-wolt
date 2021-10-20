package dk.fnie.wolt.openinghours.formatter

import dk.fnie.wolt.openinghours.model.internal.DayInfo
import dk.fnie.wolt.openinghours.model.internal.OpeningRange
import java.time.format.DateTimeFormatter

/**
 * Formats a list of DayInfos into a human-readable format.
 */
object OpeningHoursFormatter {
    private val formatter = DateTimeFormatter.ofPattern("h:mm a")

    fun List<DayInfo>.formatted(): String {
        val target = StringBuilder()
        this.forEach { formatOpeningHours(it, target) }
        return target.toString()
    }

    private fun formatOpeningHours(source: DayInfo, target: StringBuilder) {
        target.append(source.day.name.replaceFirstChar { it.titlecase() }).append(": ")
        if (source.openingHours.isEmpty()) {
            target.append("Closed")
        } else {
            target.append(source.openingHours.joinToString(transform = this::formatRange))
        }
        target.append('\n')
    }

    private fun formatRange(source: OpeningRange): String {
        return "${formatter.format(source.open)} - ${formatter.format(source.close)}".replace(":00", "")
    }
}
