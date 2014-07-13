package com.vielengames;

import android.content.Context;

import com.vielengames.VielenGamesModule;

public final class Modules {

    public static Object[] get(Context context) {
        return new Object[]{
                new VielenGamesModule(context)
        };
    }
}
