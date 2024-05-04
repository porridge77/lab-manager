package com.example.common.enums;

public enum LessonEnum {
    OK("已排课"),
    NO("未排课"),
    ;

    public String status;

    LessonEnum(String status) {
        this.status = status;
    }
}
