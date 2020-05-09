package com.nanachi.proxy.version02;

import com.nanachi.proxy.common.service.Service;

public class ServiceStrategyFactoryNoThreadSafeImpl implements ServiceStrategyFactory {

    // this should be decorated by volatile in case of multi-thread accessing
    private Service realSubject;

    @Override
    public Service realSubject(ServiceFactory serviceFactory) {
        if (realSubject == null) {
            realSubject = serviceFactory.newInstance();
        }
        return realSubject;
    }
}
