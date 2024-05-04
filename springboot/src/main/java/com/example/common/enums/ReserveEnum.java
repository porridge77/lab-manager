package com.example.common.enums;

public enum ReserveEnum {
    EMP("未审核"),
    OK("通过"),
    NO("驳回"),
    DONE("使用完毕"),
    ;

    public String status;

    ReserveEnum(String status) {
        this.status = status;
    }
}
