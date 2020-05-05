package com.fortyseven.degrees.kotlinmeetup.di

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import java.io.IOException

val LocalDateTimeFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        .withZone(ZoneId.of("UTC"))

object LocalDateTimeJsonAdapter : JsonAdapter<LocalDateTime>() {

    @Synchronized
    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): LocalDateTime? =
        if (reader.peek() == JsonReader.Token.NULL) reader.nextNull()
        else LocalDateTime.parse(reader.nextString(), LocalDateTimeFormatter)

    @Synchronized
    @Throws(IOException::class)
    override fun toJson(writer: JsonWriter, value: LocalDateTime?) {
        value?.format(LocalDateTimeFormatter)?.let(writer::value) ?: writer.nullValue()
    }
}