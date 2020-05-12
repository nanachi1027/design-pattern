package com.nanachi.proxy.version06;

/**
 * In this module both two proxies implement Service interface.
 *
 * The secure one is designed for authority checking,
 * if code is correct it will send the request msg to its member variable's proxyService's doServiceAction method.
 *
 * And the virtual proxy maintains the Service's real subject the ServiceImpl instance, in its doServiceAction method,
 * request will send to the instance directly.
 */
public class App {
    public static void main(String[] args) {
        final SecureServiceProxy secureServiceProxy = new SecureServiceProxy();

        String input = "INPUT MSG";
        secureServiceProxy.setCode("test,test123");
        String res1 = secureServiceProxy.doServiceAction(input);
        System.out.println("ret1= " + res1);

        secureServiceProxy.setCode("test,test");
        String res2 = secureServiceProxy.doServiceAction(input);
        System.out.println("ret2= " + res2);
    }
}
