package com.nanachi.proxy.version05.proxy;

import com.nanachi.proxy.version05.service.Service;

public class SecureServiceProxy implements Service {

    private Service service = null;
    private String code = null;

    public void setCode(String userPswd) {
        this.code = userPswd;
    }

    public SecureServiceProxy(Builder builder) {
        this.service = builder.service;
        this.code = builder.code;
    }

    public static Builder newBuider() {
        return new Builder();
    }

    public static Builder newBuilder(SecureServiceProxy copy) {
        Builder builder = new Builder();
        builder.code = copy.code;
        builder.service = copy.service;
        return builder;
    }

    @Override
    public String doAction(String msg) {
        if (authCheck()) {
            return service.doAction(msg);
        } else {
            return "Authority check failed: " + this.code;
        }
    }

    private boolean authCheck() {
        return code != null && code.equals("test,test123");
    }

    /**
     * Builder Pattern only used to construct an instance in steps.
     *
     * When parameters are too many to declare in a constructor,
     * we use Builder Pattern to send the parameters one by one.
     *
     * And it is better to declare Builder as a static class
     * together with the class which needs too many parameters.
     *
     * You can also take the Builder as a collector of all the parameters the class needs.
     */
    public static final class Builder {
        private Service service;
        private String code;

        private Builder() {
        }

        public Builder withService(Service service) {
            this.service = service;
            return this;
        }

        public Builder withCode(String code) {
            this.code = code;
            return this;
        }

        public SecureServiceProxy build() {
            return new SecureServiceProxy(this);
        }
    }
}
