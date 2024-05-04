package com.example.common.enums;

public enum LabTypeEnum {
    HARDWARE("硬件"),
    SOFTWARE("软件"),
    NETWORK("网络"),
    ;

    public String type;

    LabTypeEnum(String Type){
        this.type = type;
    }
}
