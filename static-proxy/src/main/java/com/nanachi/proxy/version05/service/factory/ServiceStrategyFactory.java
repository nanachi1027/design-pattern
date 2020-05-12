package com.nanachi.proxy.version05.service.factory;

import com.nanachi.proxy.version05.service.Service;

public interface ServiceStrategyFactory {
    Service realSubject(ServiceFactory factory);
}
