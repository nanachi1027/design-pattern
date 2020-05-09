package com.nanachi.proxy.version04.remote.service.impl;

import com.nanachi.proxy.version04.remote.service.Service;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@SOAPBinding(style = SOAPBinding.Style.RPC)
@WebService(endpointInterface = "com.nanachi.proxy.version04.remote.service.Service")
public class ServiceImpl implements Service {
    @Override
    public String doAction(String recvMsg) {
        return "Remote Service response msg is " + recvMsg;
    }
}
