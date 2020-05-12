package com.nanachi.proxy.version06;

import com.nanachi.proxy.common.service.Service;
import com.nanachi.proxy.common.service.ServiceImpl;

public class VirtualServiceProxy implements Service {
    private Service service = null;
    @Override
    public String doServiceAction(String input) {
        if (service == null) {
            service = new ServiceImpl();
        }
        return service.doServiceAction(input);
    }
}
