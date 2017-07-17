package com.fstyle.structure_android.data.source.remote.api.service

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

/**
 * Created by le.quang.dao on 10/03/2017.
 */

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
  override fun read(`in`: JsonReader): Int? {
    val peek = `in`.peek()
    when (peek) {
      JsonToken.NULL -> {
        `in`.nextNull()
        return null
      }

      JsonToken.NUMBER -> return `in`.nextInt()

      JsonToken.BOOLEAN -> return if (`in`.nextBoolean()) 1 else 0

      JsonToken.STRING -> {
        try {
          return Integer.valueOf(`in`.nextString())
        } catch (e: NumberFormatException) {
          return null
        }

        return null
      }
      else -> return null
    }
  }
}
