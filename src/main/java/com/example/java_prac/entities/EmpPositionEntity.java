package com.example.java_prac.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "emp_position", schema = "public", catalog = "postgres")
public class EmpPositionEntity {

    public EmpPositionEntity() {}

    public EmpPositionEntity(int pos_id,
                             Integer unit_id,
                             Integer emp_id,
                             String pos_name,
                             String pos_desc,
                             Timestamp start_timestamp,
                             Timestamp end_timestamp)
    {
        this.setPosId(pos_id);
        this.setEmpId(emp_id);
        this.setUnitId(unit_id);
        this.setPosName(pos_name);
        this.setPosDesc(pos_desc);
        this.setStartTimestamp(start_timestamp);
        this.setEndTimestamp(end_timestamp);
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "pos_id", nullable = false)
    private int posId;

    @Basic
    @Column(name = "unit_id", nullable = true)
    private Integer unitId;

    @Basic
    @Column(name = "emp_id", nullable = true)
    private Integer empId;

    @Basic
    @Column(name = "pos_name", nullable = true, length = -1)
    private String posName;

    @Basic
    @Column(name = "pos_desc", nullable = true, length = -1)
    private String posDesc;

    @Basic
    @Column(name = "start_timestamp", nullable = true)
    private Timestamp startTimestamp;

    @Basic
    @Column(name = "end_timestamp", nullable = true)
    private Timestamp endTimestamp;

    public int getPosId() {
        return posId;
    }

    public void setPosId(int posId) {
        this.posId = posId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }

    public String getPosDesc() {
        return posDesc;
    }

    public void setPosDesc(String posDesc) {
        this.posDesc = posDesc;
    }

    public Timestamp getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Timestamp startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Timestamp getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Timestamp endTimestamp) {
        this.endTimestamp = endTimestamp;
    }
}
