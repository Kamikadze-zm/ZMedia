package ru.kamikadze_zm.zmedia.model.entity.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

public class QualitySerializer<E extends Enum<E> & Quality> extends StdSerializer<E> {

    public QualitySerializer(Class<E> c) {
        super(c);
    }

    @Override
    public void serialize(E quality, JsonGenerator generator, SerializerProvider provider) throws IOException, JsonProcessingException {
        generator.writeStartObject();
        generator.writeStringField("id", quality.toString());
        generator.writeStringField("quality", quality.getQuality());
        generator.writeEndObject();
    }
}
