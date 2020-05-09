package com.nanachi.proxy.version04.remote.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface Service {
    @WebMethod
    public String doAction(String recvMsg);
}
