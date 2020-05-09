package com.nanachi.proxy.version03;

import com.nanachi.proxy.common.service.Service;
import com.nanachi.proxy.common.service.ServiceImpl;

public class ServiceSecurityProxy implements Service {

    private Service service = null;
    private String userpassword;

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    // this extra operation which checks the
    // security of input msg is set in front of Service's method
    private boolean securityAccessCheck(String userpassword) {
       return userpassword != null && userpassword.equals("aimer,54390ewqyu");
    }

    public ServiceSecurityProxy() {
        this.service = new ServiceImpl();
    }

    @Override
    public String doServiceAction(String input) {
        if (securityAccessCheck(this.userpassword)) {
            return "Service executor dealing with the input: "
                    + input + ", Response: " + service.doServiceAction(input);
        } else {
            return "User: " + userpassword.split(",")[0]
                    + " doesn't has the authority to call the service";
        }
    }
}
