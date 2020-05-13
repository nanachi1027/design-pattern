package com.nanachi.proxy.dynamic.adapters;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class ExtendedInvocationHandler<T> implements InvocationHandler {

    // key: MethodIdentifier, value: Method
    private Map<MethodIdentifier, Method> adapterMethods = new HashMap<MethodIdentifier, Method>();
    // key: MethodIdentifier, value: the object inside which method defined
    private Map<MethodIdentifier, Object> adapters = new HashMap<MethodIdentifier, Object>();
    private T original;

    public void setOriginal(T original) {
        this.original = original;
    }

    // extract all declared methods from adapter to local cache.
    public void addAdapter(Object adapter) {
        final Class<?> adapterClass = adapter.getClass();
        Method[] methods = adapterClass.getDeclaredMethods();
        for (Method m : methods) {
            final MethodIdentifier key = new MethodIdentifier(m);
            adapterMethods.put(key, m);
            adapters.put(key, adapter);
        }
    }

    /**
     * Invoke corresponding method by passing params to it.
     * But first we gonna check the method cache: adaptedMethods in which
     * key is MethodIdentifier, value is Method.
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            MethodIdentifier mIdentifierAsKey = new MethodIdentifier(method);
            Method methodAsValue = adapterMethods.get(mIdentifierAsKey);
            if (methodAsValue != null) {
                methodAsValue.setAccessible(true);
                final Object methodRet = methodAsValue.invoke(adapters.get(mIdentifierAsKey), args);
                methodAsValue.setAccessible(false);
                return methodRet;
            } else {
                return method.invoke(original, args);
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
