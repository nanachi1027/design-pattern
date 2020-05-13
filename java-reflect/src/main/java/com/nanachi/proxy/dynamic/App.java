package com.nanachi.proxy.dynamic;

public class App {
    public static void main(String[] args) {
        IJobHunter aliJobHunter = new AliProgrammer();
        String resume = "resume basic info";

        HeadcountProxy headcountProxy = new HeadcountProxy();
        headcountProxy.setJobHunter(aliJobHunter);
        String retInfo = ((IJobHunter) headcountProxy.createProxyObj()).huntForJob(resume);
        System.out.println(retInfo);

        IJobHunter byteDanceJobHunter = new ByteDanceProgrammer();
        headcountProxy.setJobHunter(byteDanceJobHunter);
        retInfo = ((IJobHunter) headcountProxy.createProxyObj()).huntForJob(resume);
        System.out.println(retInfo);

        IJobHunter tencentJobHunter = new TencentProgrammer();
        headcountProxy.setJobHunter(tencentJobHunter);
        retInfo = ((IJobHunter) headcountProxy.createProxyObj()).huntForJob(resume);
        System.out.println(retInfo);
    }
}
