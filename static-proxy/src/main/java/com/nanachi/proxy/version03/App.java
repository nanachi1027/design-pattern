package com.nanachi.proxy.version03;

import com.nanachi.proxy.common.service.Service;

/**
 * In this demo, we add the access authority check securityAccessCheck in ServiceSecurityProxy
 * which means only user & password is correct will the service be executed.
 */
public class App {
    public static void main(String[] args) {
        Service serviceProxy = new ServiceSecurityProxy();

        ((ServiceSecurityProxy) serviceProxy).setUserpassword("aimer,54390ewqyu");
        System.out.println(serviceProxy.doServiceAction("Hello"));

        ((ServiceSecurityProxy) serviceProxy).setUserpassword("aimer,333333");
        System.out.println(serviceProxy.doServiceAction("Hello"));
    }
}
