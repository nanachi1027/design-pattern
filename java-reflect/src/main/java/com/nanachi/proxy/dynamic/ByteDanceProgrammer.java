package com.nanachi.proxy.dynamic;

public class ByteDanceProgrammer implements IJobHunter {
    public String huntForJob(String resume) {
        return ByteDanceProgrammer.class.getSimpleName() + " hunts for job with resume:" + resume ;
    }
}
