package com.nanachi.proxy.version05.service.impl;


import com.nanachi.proxy.version05.service.Service;

public class ServiceImpl implements Service {

    /**
     * Implement Real Subject's processing logic in this method.
     */
    @Override
    public String doAction(String msg) {
        return "Service ret is " + msg;
    }
}
