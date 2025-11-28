package com.aedescontrol.backend.config;

import com.aedescontrol.backend.exception.InvalidStatusException;
import com.aedescontrol.backend.model.Address;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class StatusDeserializer extends JsonDeserializer<Address.Status> {

    @Override
    public Address.Status deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        String value = p.getText().toUpperCase();
        try {
            return Address.Status.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusException(value);
        }
    }
}
