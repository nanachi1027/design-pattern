package com.nanachi.proxy.version04;

import com.nanachi.proxy.version04.remote.proxy.ServiceRemoteProxy;
import com.nanachi.proxy.version04.remote.service.Service;

import java.time.Duration;
import java.time.LocalDateTime;

public class App {
    public static void main(String[] args) {
        Service proxy = new ServiceRemoteProxy();
        final LocalDateTime startTimestamp = LocalDateTime.now();
        System.out.println("Remote service start timestamp=" + startTimestamp);
        String request = "Hello";
        System.out.println("Response: " + proxy.doAction(request));
        final LocalDateTime stopTimestamp = LocalDateTime.now();
        System.out.println("Remote service stop timestamp=" + stopTimestamp);
        final Duration serviceDuration = Duration.between(startTimestamp, stopTimestamp);
        System.out.println("Remote service duration:" + serviceDuration);
    }
}
