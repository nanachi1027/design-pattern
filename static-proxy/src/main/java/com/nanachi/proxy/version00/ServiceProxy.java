package com.nanachi.proxy.version00;

import com.nanachi.proxy.common.service.Service;
import com.nanachi.proxy.common.service.ServiceImpl;

public class ServiceProxy implements Service {
    private Service service;

    public ServiceProxy() {
        service = new ServiceImpl();
    }

    @Override
    public String doServiceAction(String input) {
        return prefixCheck(input);
    }

    // here we check whether the inupt msg contain the special prefix 'spark'
    // if so, add 'hadoop' prefix, or do nothing
    private String prefixCheck(String input) {
        if (input.startsWith("spark.")) {
            input =  "hadoop." + input;
        }
        return input;
    }
}
