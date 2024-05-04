package com.example.common.enums;

public enum StatusEnum {
    OK("空闲"),
    NO("被占用"),
            ;

    public String status;

    StatusEnum(String status) {
        this.status = status;
    }
}
