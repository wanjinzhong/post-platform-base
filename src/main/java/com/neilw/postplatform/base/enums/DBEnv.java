package com.neilw.postplatform.base.enums;

public enum DBEnv {
    DEV,
    DEV_US,
    DEV_UK,
    DEV_CN,
    DEV_WW,
    UAT,
    UAT_US,
    UAT_UK,
    UAT_CN,
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
