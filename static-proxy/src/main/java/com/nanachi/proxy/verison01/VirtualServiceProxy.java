package com.nanachi.proxy.verison01;

import com.nanachi.proxy.common.service.Service;
import com.nanachi.proxy.common.service.ServiceImpl;

public class VirtualServiceProxy implements Service {

    // this gonna be no-thread safe when multi-thread
    // try to execute doServiceAction
    private Service service = null;

    @Override
    public String doServiceAction(String input) {
        if (service == null) {
            service = new ServiceImpl();
        }
        return service.doServiceAction(input);
    }
}
