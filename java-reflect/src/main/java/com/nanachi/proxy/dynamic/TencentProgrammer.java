package com.nanachi.proxy.dynamic;

public class TencentProgrammer implements IJobHunter {

    public String huntForJob(String resume) {
        return this.getClass().getSimpleName() + " hunts for job with resume:" + resume ;
    }
}
