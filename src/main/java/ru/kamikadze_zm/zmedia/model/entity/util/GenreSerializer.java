package ru.kamikadze_zm.zmedia.model.entity.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

public class GenreSerializer<E extends Enum<E> & Genre> extends StdSerializer<E> {

    public GenreSerializer(Class<E> c) {
        super(c);
    }

    @Override
    public void serialize(E genre, JsonGenerator generator, SerializerProvider provider) throws IOException, JsonProcessingException {
        generator.writeStartObject();
        generator.writeStringField("id", genre.toString());
        generator.writeStringField("genre", genre.getGenre());
        generator.writeEndObject();
    }
}
