package com.ccc.nameapp.data.source.remote.api.middleware

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

class IntegerAdapter : TypeAdapter<Int>() {

    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: Int?) {
        if (value == null) {
            out.nullValue()
            return
        }
        out.value(value)
    }

    @Throws(IOException::class)
    override fun read(jsonReader: JsonReader): Int? {
        return when (jsonReader.peek()) {
            JsonToken.NULL -> {
                jsonReader.nextNull()
                null
            }
            JsonToken.NUMBER -> jsonReader.nextInt()
            JsonToken.BOOLEAN -> if (jsonReader.nextBoolean()) 1 else 0
            JsonToken.STRING -> {
                jsonReader.nextString().toInt()
            }
            else -> null
        }
    }
}
