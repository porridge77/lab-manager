package com.example.entity;

import com.example.common.enums.LabTypeEnum;

public class Lab {
    private static final long serialVersionUID = 1L;

    /** 实验室ID*/
    private Integer id;
    /** 实验室编号*/
    private String no;
    /** 名称*/
    private String name;
    /** 类型*/
    private String type;
    /** 设备数*/
    private Integer equipmentNum;
    /** 管理员id*/
    private Integer adminId;
    /** 管理员姓名*/
    private String adminName;
    /** 使用状态*/
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getEquipmentNum() {
        return equipmentNum;
    }

    public void setEquipmentNum(Integer equipmentNum) {
        this.equipmentNum = equipmentNum;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
}
