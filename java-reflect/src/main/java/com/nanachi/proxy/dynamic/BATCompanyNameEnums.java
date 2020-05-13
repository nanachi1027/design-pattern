package com.nanachi.proxy.dynamic;

public enum BATCompanyNameEnums {
    BYTEDANCE((byte) 0), ALIBABA((byte) 1), TENCENT((byte) 2);

    private final byte companyId;

    BATCompanyNameEnums(byte b) {
        this.companyId = b;
    }

    public static String getCompanyName(BATCompanyNameEnums id) {
        switch (id.companyId) {
            case 0:
                return "BYTEDANCE";
            case 1:
                return "ALIBABA";
            case 2:
                return "TENCENT";
            default:
                return "UNKNOWN";
        }
    }
}
