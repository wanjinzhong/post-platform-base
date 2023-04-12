package com.neilw.postplatform.base.enums;

public enum DBEnv {
    UAT,
    UAT_US,
    UAT_UK,
    UAT_CK,
    UAT_WW,
    PROD,
    PROD_US,
    PROD_UK,
    PROD_CN,
    PROD_WW;

    public String lower() {
        return name().toLowerCase();
    }

    public String upper() {
        return name().toUpperCase();
    }
}
