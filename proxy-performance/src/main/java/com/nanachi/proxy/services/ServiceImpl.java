package com.nanachi.proxy.services;

import java.time.LocalDateTime;

public class ServiceImpl implements Service {
    @Override
    public String doAction(String msg, LocalDateTime timestamp) {
        return "Service result: " + msg + " timestamp: " + timestamp;
    }
}
