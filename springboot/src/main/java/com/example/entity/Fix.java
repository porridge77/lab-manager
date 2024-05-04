package com.example.entity;

import java.io.Serializable;
import java.util.Date;

public class Fix implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer teacherId;
    private Integer labId;
    private Integer labAdminId;
    private String description;
    private String status;
    private Date date;
    private String statement;

    private String teacherName;
    private String labNo;
    private String labAdminName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getLabId() {
        return labId;
    }

    public void setLabId(Integer labId) {
        this.labId = labId;
    }

    public Integer getLabAdminId() {
        return labAdminId;
    }

    public void setLabAdminId(Integer labAdminId) {
        this.labAdminId = labAdminId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getLabNo() {
        return labNo;
    }

    public void setLabNo(String labNo) {
        this.labNo = labNo;
    }

    public String getLabAdminName() {
        return labAdminName;
    }

    public void setLabAdminName(String labAdminName) {
        this.labAdminName = labAdminName;
    }
}
