package de.door2door.androidcandidatetest.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateHelper {
    private const val OUTPUT_DATE_FORMAT = "HH:mm"
    private const val INPUT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
    private val simpleDateFormatInput = SimpleDateFormat(INPUT_DATE_FORMAT, Locale.ENGLISH)

    fun formatDate(date: String): String {
        val simpleDateFormatOutput = SimpleDateFormat(OUTPUT_DATE_FORMAT, Locale.ENGLISH)
        val inputDate = simpleDateFormatInput.parse(date)
        return simpleDateFormatOutput.format(inputDate)
    }

    fun getDuration(departureTime: String, arrivalTime: String): Long {
        val arrivalDate = simpleDateFormatInput.parse(arrivalTime)
        val departureDate = simpleDateFormatInput.parse(departureTime)
        val duration = arrivalDate.time - departureDate.time
        return TimeUnit.MILLISECONDS.toMinutes(duration)
    }
}