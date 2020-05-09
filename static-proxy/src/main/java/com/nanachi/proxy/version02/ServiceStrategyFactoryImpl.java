package com.nanachi.proxy.version02;

import com.nanachi.proxy.common.service.Service;

public class ServiceStrategyFactoryImpl implements ServiceStrategyFactory {
    @Override
    public Service realSubject(final ServiceFactory serviceFactory) {
        return serviceFactory.newInstance();
    }
}
