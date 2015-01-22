package com.vielengames.parser;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.vielengames.data.Game;
import com.vielengames.data.kuridor.KuridorGame;

import java.lang.reflect.Type;

public final class GameJsonDeserializer implements JsonDeserializer<Game> {

    @Override
    public Game deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return context.deserialize(json, KuridorGame.class);
    }
}
