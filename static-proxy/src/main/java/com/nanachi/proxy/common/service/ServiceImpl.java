package com.nanachi.proxy.common.service;

import java.time.LocalTime;

public class ServiceImpl implements Service {

    public ServiceImpl() {
        System.out.println("ServiceImpl init at " + LocalTime.now());
    }

    public String doServiceAction(String input) {
        return "DoServiceAction by receiving the input msg=" + input;
    }
}
