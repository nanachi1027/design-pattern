package com.nanachi.proxy.dynamic.adapters;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * MethodIdentifier is a wrapper of Method, and it provides a simple way for
 * callers to acquire a Method parameters, name and whether two methods are
 * equal(which means two methods own the same method name and parameters) to each other in a quickly way.
 */
public class MethodIdentifier {
    private final String name;
    private final Class[] parameters;

    public MethodIdentifier(Method m) {
        name = m.getName();
        parameters = m.getParameterTypes();
    }

    public int hashCode() {
        return name.hashCode();
    }

    // we can save time by assuming that we only compare against
    // other MethodIdentifier objects
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodIdentifier that = (MethodIdentifier) o;
        return name.equals(that.name) &&
                Arrays.equals(parameters, that.parameters);
    }
}
