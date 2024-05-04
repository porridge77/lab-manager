package com.example.common.enums;

public enum FixEnum {
    NOT("未维修"),
    ING("维修中"),
    DONE("已维修"),
    ;

    public String status;

    FixEnum(String status) {
        this.status = status;
    }
}
