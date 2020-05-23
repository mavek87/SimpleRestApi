package com.matteoveroni.simplerestapi.utils;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import java.io.IOException;
import java.io.StringReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtils {

    private static final Logger LOG = LoggerFactory.getLogger(JsonUtils.class);

    public boolean isValid(String json) {
        if (json == null || json.isEmpty()) {
            throw new IllegalArgumentException("null or empty json in input");
        }
        JsonReader jsonReader = new JsonReader(new StringReader(json));
        JsonToken token;
        try {
            boolean isEndDocument = false;
            while (jsonReader.hasNext() && !isEndDocument) {
                token = jsonReader.peek();
                switch (token) {
                    case BEGIN_ARRAY:
                        jsonReader.beginArray();
                        break;
                    case END_ARRAY:
                        jsonReader.endArray();
                        break;
                    case BEGIN_OBJECT:
                        jsonReader.beginObject();
                        break;
                    case END_OBJECT:
                        jsonReader.endObject();
                        break;
                    case NAME:
                        jsonReader.nextName();
                        break;
                    case STRING:
                    case NUMBER:
                    case BOOLEAN:
                    case NULL:
                        jsonReader.skipValue();
                        break;
                    case END_DOCUMENT:
                        isEndDocument = true;
                        break;
                    default:
                        throw new AssertionError(token);
                }
            }
        } catch (IOException ex) {
            LOG.error("Error", ex);
            return false;
        }
        return true;
    }
}
