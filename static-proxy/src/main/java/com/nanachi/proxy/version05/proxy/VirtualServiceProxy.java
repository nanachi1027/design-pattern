package com.nanachi.proxy.version05.proxy;

import com.nanachi.proxy.version05.service.Service;
import com.nanachi.proxy.version05.service.factory.ServiceFactory;
import com.nanachi.proxy.version05.service.factory.ServiceStrategyFactory;

public class VirtualServiceProxy implements Service {

    public ServiceFactory serviceFactory;
    public ServiceStrategyFactory strategyFactory;

    private VirtualServiceProxy(Builder builder) {
        // serviceFactory = builder.serviceFactory;
        // strategyFactory = serviceFactory.serviceStrategy;
    }

    @Override
    public String doAction(String msg) {
        return null;
    }

    public static final class Builder {
        public VirtualServiceProxy build() {
            return new VirtualServiceProxy(this);
        }

    }


}
