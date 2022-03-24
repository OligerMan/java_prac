package com.example.java_prac.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "employee", schema = "public", catalog = "postgres")
public class EmployeeEntity {

    public EmployeeEntity() {}

    public EmployeeEntity(int emp_id, String home_address, String emp_name, Integer current_position_id) {
        this.setEmpId(emp_id);
        this.setHomeAddress(home_address);
        this.setEmpName(emp_name);
        this.setCurrentPositionId(current_position_id);
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "emp_id", nullable = false)
    private int empId;

    @Basic
    @Column(name = "home_address", nullable = true, length = -1)
    private String homeAddress;

    @Basic
    @Column(name = "emp_name", nullable = true, length = -1)
    private String empName;

    @Basic
    @Column(name = "current_position_id", nullable = true)
    private Integer currentPositionId;

    /*@OneToMany(mappedBy = "employeeByEmpId")
    private Collection<EmpPositionEntity> empPositionsByEmpId;*/

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getCurrentPositionId() {
        return currentPositionId;
    }

    public void setCurrentPositionId(Integer currentPositionId) {
        this.currentPositionId = currentPositionId;
    }

    /*public Collection<EmpPositionEntity> getEmpPositionsByEmpId() {
        return empPositionsByEmpId;
    }

    public void setEmpPositionsByEmpId(Collection<EmpPositionEntity> empPositionsByEmpId) {
        this.empPositionsByEmpId = empPositionsByEmpId;
    }*/
}
