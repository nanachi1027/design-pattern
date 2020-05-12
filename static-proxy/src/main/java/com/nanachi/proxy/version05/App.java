package com.nanachi.proxy.version05;

import com.nanachi.proxy.version05.proxy.SecureServiceProxy;
import com.nanachi.proxy.version05.proxy.VirtualServiceProxy;
import com.nanachi.proxy.version05.service.Service;
import com.nanachi.proxy.version05.service.factory.ServiceFactory;
import com.nanachi.proxy.version05.service.factory.ServiceStrategyFactory;
import com.nanachi.proxy.version05.service.impl.ServiceImpl;

/**
 * Service has two proxies: the secure on is implemented for add extra authority checking steps before call doAction method.
 * And the virtual proxy one is implemented for decoupling ServiceImpl and ServiceFactory by adding Strategy Pattern
 * in which we can construct different Service implementation instances by calling corresponding services factories.
 *
 * We also use builder pattern which declared as static class in each proxy's class.
 * Builder pattern can encapsulate complex steps especially caused by multiple parameters.
 */

public class App {
    public static void main(String[] args) throws InterruptedException {
        final ServiceStrategyFactory strategyFactory = new ServiceStrategyFactory() {
            Service service = null;
            @Override
            public Service realSubject(ServiceFactory factory) {
                if (service == null) {
                    service = factory.newInstance();
                }
                return service;
            }
        };

        final VirtualServiceProxy.Builder virtualServiceProxyBuilder = VirtualServiceProxy.newBuilder()
                .withServiceFactory(new ServiceFactory() {
                    private Service service = null;
                    @Override
                    public Service newInstance() {
                        if (service == null) {
                            service = new ServiceImpl();
                        }
                        return service;
                    }
                })
                .withServiceStrategyFactory(strategyFactory);

        final SecureServiceProxy.Builder secureServiceProxyBuilder = SecureServiceProxy.newBuider()
                .withService(virtualServiceProxyBuilder.build())
                .withCode("test,test123");

        final SecureServiceProxy proxy = secureServiceProxyBuilder.build();

        Thread.sleep(5000);

        String response1 = proxy.doAction("Request 1 to service");
        System.out.println("response: " + response1);

        String response2 = proxy.doAction("Request 2 to service");
        System.out.println("response: " + response2);

        // reset code in Secure Proxy
        proxy.setCode("test,test");
        response1 = proxy.doAction("Request 1 to service");
        System.out.println("res1= " + response1);
        response2 = proxy.doAction("Request 2 to service");
        System.out.println("res2= " + response2);
    }
}
