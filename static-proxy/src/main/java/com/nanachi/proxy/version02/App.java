package com.nanachi.proxy.version02;

import com.nanachi.proxy.common.service.Service;
import com.nanachi.proxy.common.service.ServiceImpl;

/**
 * In this demo we add the extra operation: 'prefixCheck' in which
 * some logic validation will be execute before Service's doServiceAction in proxy.
 *
 * ServiceFactory & ServiceStrategyFactory these two interfaces define
 * how Service's implementation instances are created and called.
 *
 * Both ServiceStrategyFactoryImpl and ServiceStrategyFactoryImpl implement
 * ServiceStrategyFactory this interface and its method declared inside in it.
 */
public class App {
    public static void main(String[] args) {
        Service serviceProxy = new ServiceProxy();
        final String input1 = "Hello";
        final String input2 = "spark.conf.dir";

        String output1 = serviceProxy.doServiceAction(input1);
        String output2 = serviceProxy.doServiceAction(input2);

        System.out.println(output1);
        System.out.println(output2);
    }

    public static class ServiceProxy implements Service {

        private ServiceFactory serviceFactory;
        private ServiceStrategyFactory strategyFactory;

        public ServiceProxy() {
            serviceFactory = ServiceImpl::new;
            strategyFactory = new ServiceStrategyFactoryNoThreadSafeImpl();
        }

        private String prefixCheck(String input) {
            if (input.startsWith("spark.")) {
                return "hadoop." + input.trim();
            }
            return input;
        }

        // As proxy we do the extra operations here
        @Override
        public String doServiceAction(String input) {
            return strategyFactory.realSubject(serviceFactory).doServiceAction(prefixCheck(input));
        }
    }
}
