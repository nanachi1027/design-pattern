package com.nanachi.proxy.version04.remote.service;

import com.nanachi.proxy.version04.remote.service.impl.ServiceImpl;

import javax.xml.ws.Endpoint;
import java.util.concurrent.Executors;

/**
 * ServicePublish is working as a remote service provider:
 * 1. create service implementation instance
 * 2. set the service URL address as a endpoint and publish the service to it.
 * 3. in case of multi-client send request to the endpoint we create a thread pool
 *    and bind it to the endpoint.
 */
public class ServicePublisher {
    public static void main(String[] args) {
        final String servicePublishAddr = "http://localhost:9999/ws/service";
        final Endpoint serviceEndpoint = Endpoint.create(new ServiceImpl());
        serviceEndpoint.setExecutor(Executors.newFixedThreadPool(10));

        // After we execute this, the service instance will be bind to the URL address.
        serviceEndpoint.publish(servicePublishAddr);

        System.out.println("Service state: " + serviceEndpoint.isPublished());
        System.out.println("Service is working on = " + servicePublishAddr);
    }
}
