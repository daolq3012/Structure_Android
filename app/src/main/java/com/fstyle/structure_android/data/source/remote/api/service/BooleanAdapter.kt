package com.fstyle.structure_android.data.source.remote.api.service

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

/**
 * Created by le.quang.dao on 10/03/2017.
 */

class BooleanAdapter : TypeAdapter<Boolean>() {
  @Throws(IOException::class)
  override fun write(out: JsonWriter, value: Boolean?) {
    if (value == null) {
      out.nullValue()
      return
    }
    out.value(value)
  }

  @Throws(IOException::class)
  override fun read(`in`: JsonReader): Boolean? {
    val peek = `in`.peek()
    when (peek) {
      JsonToken.NULL -> {
        `in`.nextNull()
        return null
      }

      JsonToken.BOOLEAN -> return `in`.nextBoolean()

      JsonToken.NUMBER -> return `in`.nextInt() != 0

      JsonToken.STRING -> return java.lang.Boolean.valueOf(`in`.nextString())

      else -> return null
    }
  }
}
