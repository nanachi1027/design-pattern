package com.nanachi.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Headcount is a proxy of programmer when he/she hunts for a job.
 */
public class HeadcountProxy implements InvocationHandler {

    private IJobHunter jobHunter = null;

    public void setJobHunter(IJobHunter jobHunter) {
        this.jobHunter = jobHunter;
    }

    // Here we get an instance of dynamic proxy of IJobHunter implementations in Object.
    // By transfer this Object to IJobHunter, we can send the method request to the interface implementations.
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Hunting for job");
        return method.invoke(jobHunter, args);
    }

    /**
     * This method return the instance which is the actual proxy we created.
     * These are three parameters necessary when we call Proxy.newProxyInstance(
     * 1. ClassLoader
     * 2. Class [] interfaces organized in an array
     * 3. class instance which implements InvocationHandler.
     * )
     *
     * All methods defined in specifical interfaces should be implemented by the instance
     * which we created by jave reflection in InvocationHandler.
     *
     * What we got from the invoke is the so called dynamic proxy of
     * {TecentProgrammer, AliProgrammer, ByteDanceProgrammer}
     * All methods defined inside the IJobHunter are extracted from the actual instance {Ali, ByteDance, Tecent...};
     */
    public IJobHunter createProxyObj() {
        // create instance which implements all methods defined inside IJobHunter
        // params
        // 1: class loader;
        // 2. interfaces in type of Class [];
        // 3. class instance which implement InvocationHandler's invoke method
        return (IJobHunter) Proxy.newProxyInstance(jobHunter.getClass().getClassLoader(),
                jobHunter.getClass().getInterfaces(), this);
    }
}
