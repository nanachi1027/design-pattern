package com.nanachi.proxy.version06;

import com.nanachi.proxy.common.service.Service;

public class SecureServiceProxy implements Service {

    private Service serviceProxy = null;
    private String code = null;

    public SecureServiceProxy() {
        serviceProxy = new VirtualServiceProxy();
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String doServiceAction(String input) {
        if (code != null && code.equals("test,test123")) {
            return serviceProxy.doServiceAction(input);
        } else {
            return "Authority access is denied when code = " + this.code;
        }
    }
}
