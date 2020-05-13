package com.nanachi.proxy.dynamic;

import com.nanachi.proxy.dynamic.services.Service;
import com.nanachi.proxy.dynamic.services.ServiceImpl;

public class App {
    public static void serviceInvoker(final Service serviceImpl) {
        String requestRet = serviceImpl.request("Request Msg");
        System.out.println(requestRet);

        String responseRet = serviceImpl.response("Response Msg");
        System.out.println(responseRet);
    }

    public static void main(String[] args) {
        Service service = new ServiceImpl();
        ServiceStaticObjectAdapter serviceAdapter = new ServiceStaticObjectAdapter();

        serviceAdapter.addService(service);
        serviceAdapter.setRequestServiceAdapter(new ServiceStaticObjectAdapter.IRequestServiceAdapter() {
            public String request(String msg) {
                return "Request adapter " + msg;
            }
        });
        serviceAdapter.setResponseServiceAdapter(new ServiceStaticObjectAdapter.IResponseServiceAdapter() {
            public String response(String msg) {
                return "Response adapter " + msg;
            }
        });
        serviceInvoker(service);

        System.out.println("==========");

        // Ok, here we set both of the two adapter null
        serviceAdapter.setResponseServiceAdapter(null);
        serviceAdapter.setRequestServiceAdapter(null);
        serviceInvoker(serviceAdapter);
    }

    public static class ServiceStaticObjectAdapter implements Service {
        // Here we go two Service implementation
        // Service (ServiceImpl) which implements both of the methods;
        // IRequestServiceAdapter which implements only request declared in Service;
        // IResponseServiceAdpater which implements only response method.

        private Service service;
        private IRequestServiceAdapter requestServiceAdapter;
        private IResponseServiceAdapter responseServiceAdapter;

        public void addService(final Service serviceImpl) {
            this.service = serviceImpl;
        }

        public void setRequestServiceAdapter(final IRequestServiceAdapter adapter) {
            this.requestServiceAdapter = adapter;
        }

        public void setResponseServiceAdapter(final IResponseServiceAdapter adapter) {
            this.responseServiceAdapter = adapter;
        }

        // so, every time when request is called, we have two choice:
        // if the adapter is not null, logic will be delegated to the adpater
        // or else service will execute the request
        public String request(String msg) {
            if (responseServiceAdapter != null) {
                return requestServiceAdapter.request(msg);
            } else {
                return service.request(msg);
            }
        }

        // response is the same as the request
        public String response(String msg) {
            if (responseServiceAdapter != null) {
                return responseServiceAdapter.response(msg);
            } else {
                return service.response(msg);
            }
        }

        public interface IRequestServiceAdapter {
            String request(final String msg);
        }

        public interface IResponseServiceAdapter {
            String response(final String msg);
        }
    }


}