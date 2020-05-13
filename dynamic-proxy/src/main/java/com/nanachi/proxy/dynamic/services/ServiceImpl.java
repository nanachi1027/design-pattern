package com.nanachi.proxy.dynamic.services;

public class ServiceImpl implements Service {

    public String request(String msg) {
        return this.getClass().getSimpleName() + " send request msg = " + msg;
    }

    public String response(String msg) {
        return this.getClass().getSimpleName() + " receive request msg = " + msg;
    }
}
