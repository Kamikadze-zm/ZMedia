package ru.kamikadze_zm.zmedia.jwtauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

abstract class JsonHandler {

    private final ObjectMapper objectMapper;

    public JsonHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    protected void write(HttpServletResponse response, int status, Object objectToWrite) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        objectMapper.writeValue(response.getWriter(), objectToWrite);
    }
}
