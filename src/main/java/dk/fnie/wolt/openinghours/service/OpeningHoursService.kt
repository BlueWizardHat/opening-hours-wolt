package dk.fnie.wolt.openinghours.service

import dk.fnie.wolt.openinghours.converter.OpeningHoursConverter.toDayInfo
import dk.fnie.wolt.openinghours.formatter.OpeningHoursFormatter.formatted
import dk.fnie.wolt.openinghours.model.external.WeekData
import mu.KotlinLogging
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Service for receiving a JSON format of opening and closing times and formatting it into a human-readable format.
 */
@RestController
@RequestMapping("/api/openinghours")
class OpeningHoursService {
    private val log = KotlinLogging.logger {}

    @PostMapping(path = ["/format"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.TEXT_PLAIN_VALUE])
    fun formatOpeningHours(@RequestBody weekData: WeekData): String {
        log.debug { "formatOpeningHours(): weekData = $weekData" }
        return weekData.toDayInfo().formatted()
    }
}
