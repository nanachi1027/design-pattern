package com.nanachi.proxy.version00;

import com.nanachi.proxy.common.service.Service;

public class App {
    public static void main(String[] args) {
        String input1 = "Hello";
        String input2 = "spark.executor.cores";
        Service service = new ServiceProxy();

        System.out.println(input1.equals(service.doServiceAction(input1)));
        System.out.println("hadoop.spark.executor.cores".equals(service.doServiceAction(input2)));
    }
}
