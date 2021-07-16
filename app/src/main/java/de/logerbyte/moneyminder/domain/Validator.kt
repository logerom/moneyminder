package de.logerbyte.moneyminder.domain

import de.logerbyte.moneyminder.DATE_PATTERN
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Validator @Inject constructor(){

    fun isDateTextCorrect(date: String): Boolean {
        try {
            LocalDate.parse(
                date, DateTimeFormatter.ofPattern(
                    DATE_PATTERN
                )
            )
        }catch (e: DateTimeParseException){
            return false
        }
        return true
    }
}

fun String?.textOrBlank(): String = this ?:""
