package com.nanachi.proxy.services;

import com.nanachi.proxy.annotations.Delegator;
import com.nanachi.proxy.annotations.VirtualProxy;
import com.nanachi.proxy.annotations.VirtualProxyNotThreadSave;

import java.time.LocalDateTime;

@Delegator
@VirtualProxy
@VirtualProxyNotThreadSave
public interface Service {
    String doAction(String msg, LocalDateTime timestamp);
}
