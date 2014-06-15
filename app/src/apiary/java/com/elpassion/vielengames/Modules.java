package com.elpassion.vielengames;

import android.content.Context;

public final class Modules {

    public static Object[] get(Context context) {
        return new Object[]{
                new VielenGamesModule(context)
        };
    }
}
