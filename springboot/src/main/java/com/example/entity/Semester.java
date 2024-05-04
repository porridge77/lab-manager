package com.example.entity;

public class Semester {
    private static final long serialVersionUID = 1L;
    /** ID*/
    private Integer id;
    /** 名称*/
    private String name;
    /** 周数*/
    private Integer weekCount;
    /** 当前学期*/
    private Boolean isCurrent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeekCount() {
        return weekCount;
    }

    public void setWeekCount(Integer weekCount) {
        this.weekCount = weekCount;
    }

    public Boolean getCurrent() {
        return isCurrent;
    }

    public void setCurrent(Boolean current) {
        isCurrent = current;
    }
}
