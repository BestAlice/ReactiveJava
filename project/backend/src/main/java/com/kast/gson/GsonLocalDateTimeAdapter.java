package com.kast.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author Kirill "Tamada" Simovin
 */
public class GsonLocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    @Override
    public void write(@NotNull JsonWriter jsonWriter, @NotNull LocalDateTime localDateTime) throws IOException {
        jsonWriter.value(localDateTime.toString());
    }

    @Override
    public LocalDateTime read(JsonReader jsonReader) {
        return LocalDateTime.now();
    }
}
