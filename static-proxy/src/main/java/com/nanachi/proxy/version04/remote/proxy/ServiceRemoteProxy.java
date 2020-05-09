package com.nanachi.proxy.version04.remote.proxy;

import com.nanachi.proxy.version04.remote.service.Service;
import com.nanachi.proxy.version04.remote.service.impl.ServiceImpl;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Duty of this remote proxy is establishing connection to target endpoint
 * and send request to and recv from the remote service.
 */
public class ServiceRemoteProxy implements Service {

    private Service remoteService = null;
    private URL remoteURL = null;

    public ServiceRemoteProxy() {
        this.remoteService = new ServiceImpl();
    }

    @Override
    public String doAction(String recvMsg) {
        if (remoteService == null) {
            doConnect();
        }
        return remoteService.doAction(recvMsg);
    }

    private boolean doConnect() {
        try {
            this.remoteURL = new URL("http://localhost:9999/ws/service?wsdl");
            // namespaceURL is in reverse order of the package: com.nanachi.proxy.version04.remote.service.impl
            final String namespaceURL = "http://impl.service.remote.version04.proxy.nanachi.com/";
            // localPart is the class name which implements the method declared inside the specifical Service
            final String localPart = "ServiceImplService";
            QName qname = new QName(namespaceURL, localPart);
            javax.xml.ws.Service service = javax.xml.ws.Service.create(remoteURL, qname);
            remoteService = service.getPort(Service.class);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return remoteService != null;
    }
}
