package com.vielengames.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class Sets {

    private Sets() {
    }

    public static <T> Set<T> set() {
        return Collections.emptySet();
    }

    public static <T> Set<T> set(T item) {
        return Collections.singleton(item);
    }

    public static <T> Set<T> set(T... items) {
        return Collections.unmodifiableSet(new HashSet<T>(Arrays.asList(items)));
    }
}
