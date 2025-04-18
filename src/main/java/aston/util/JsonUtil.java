package aston.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public static <T> T read(HttpServletRequest req, Class<T> clazz) throws IOException {
        return objectMapper.readValue(req.getInputStream(), clazz);
    }

    public static void write(HttpServletResponse resp, Object obj) throws IOException {
        resp.setContentType("application/json");
        objectMapper.writeValue(resp.getOutputStream(), obj);
    }
}