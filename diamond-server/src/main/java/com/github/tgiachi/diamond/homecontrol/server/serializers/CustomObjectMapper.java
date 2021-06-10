package com.github.tgiachi.diamond.homecontrol.server.serializers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.bson.types.ObjectId;

public class CustomObjectMapper extends ObjectMapper {

    public CustomObjectMapper() {
        SimpleModule module = new SimpleModule("ObjectIdmodule");
        module.addSerializer(ObjectId.class, new ObjectIdSerializer());
        this.registerModule(module);
    }

}