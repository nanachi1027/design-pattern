package com.nanachi.proxy.dynamic.adapters;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public abstract class AdapterBuilder<T> {

    // Method to create an instance of T
    // through java native dynamic method Proxy.newProxyInstance(...)
    public T buildForTarget(Class<T> target) {
        return (T) Proxy.newProxyInstance(
                target.getClassLoader(), // param1: ClassLoader
                new Class[]{target},   // param2: interfaces organized in Class array
                getInvocationHandler()   // param3: instance of class which implements InovcationHandler
        );
    }


    // Method defined for creating instance of sub-class of ExtendedInvocationHandler
    // which is the implementation of interface: InvocationHandler
    protected abstract <I extends ExtendedInvocationHandler> I getInvocationHandler();
}
