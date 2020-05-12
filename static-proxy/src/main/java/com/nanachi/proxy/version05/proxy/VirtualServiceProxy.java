package com.nanachi.proxy.version05.proxy;

import com.nanachi.proxy.version05.service.Service;
import com.nanachi.proxy.version05.service.factory.ServiceFactory;
import com.nanachi.proxy.version05.service.factory.ServiceStrategyFactory;

public class VirtualServiceProxy implements Service {

    public ServiceFactory serviceFactory;
    public ServiceStrategyFactory strategyFactory;

    private VirtualServiceProxy(Builder builder) {
        serviceFactory = builder.serviceFactory;
        strategyFactory = builder.serviceStrategyFactory;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(VirtualServiceProxy copy) {
        Builder builder = new Builder();
        builder.serviceStrategyFactory = copy.strategyFactory;
        builder.serviceFactory = copy.serviceFactory;
        return builder;
    }

    @Override
    public String doAction(String msg) {
        return strategyFactory.realSubject(serviceFactory).doAction(msg);
    }

    public static final class Builder {
        private ServiceFactory serviceFactory;
        private ServiceStrategyFactory serviceStrategyFactory;

        private Builder() {

        }

        public Builder withServiceFactory(ServiceFactory serviceFactory) {
            this.serviceFactory = serviceFactory;
            return this;
        }

        public Builder withServiceStrategyFactory(ServiceStrategyFactory strategyFactory) {
            this.serviceStrategyFactory = strategyFactory;
            return this;
        }

        public VirtualServiceProxy build() {
            return new VirtualServiceProxy(this);
        }
    }
}
