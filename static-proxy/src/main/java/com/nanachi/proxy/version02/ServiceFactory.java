package com.nanachi.proxy.version02;

import com.nanachi.proxy.common.service.Service;

public interface ServiceFactory {
    Service newInstance();
}
