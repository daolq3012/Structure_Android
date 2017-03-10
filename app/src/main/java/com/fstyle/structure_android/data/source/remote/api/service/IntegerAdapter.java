package com.fstyle.structure_android.data.source.remote.api.service;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class IntegerAdapter extends TypeAdapter<Integer> {
    @Override
    public void write(JsonWriter out, Integer value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.value(value);
    }

    @Override
    public Integer read(JsonReader in) throws IOException {
        JsonToken peek = in.peek();
        switch (peek) {
            case NULL:
                in.nextNull();
                return null;

            case NUMBER:
                return in.nextInt();

            case BOOLEAN:
                return in.nextBoolean() ? 1 : 0;

            case STRING:
                try {
                    return Integer.valueOf(in.nextString());
                } catch (NumberFormatException e) {
                    return null;
                }
            default:
                return null;
        }
    }
}
