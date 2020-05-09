package com.nanachi.proxy.verison01;

import com.nanachi.proxy.common.service.Service;
import com.sun.tools.javac.util.Assert;

public class App {
    public static void main(String[] args) {
        Service service = new VirtualServiceProxy();
        final String output1 = service.doServiceAction("hello?");
        final String output2 = service.doServiceAction("spark.driver.address");

        Assert.check(output1.equals("hello?"));
        Assert.check(output2.equals("hadoop.spark.driver.address"));
    }
}
