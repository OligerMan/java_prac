package com.example.java_prac.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "unit", schema = "public", catalog = "postgres")
public class UnitEntity {

    public UnitEntity() {}

    public UnitEntity(int unit_id, int director_id, int higher_unit_id, String unit_name) {
        this.setUnitId(unit_id);
        this.setDirectorId(director_id);
        this.setHigherUnitId(higher_unit_id);
        this.setUnitName(unit_name);
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "unit_id", nullable = false)
    private int unitId;

    @Basic
    @Column(name = "director_id", nullable = true)
    private Integer directorId;

    @Basic
    @Column(name = "higher_unit_id", nullable = true)
    private Integer higherUnitId;

    @Basic
    @Column(name = "unit_name", nullable = true, length = -1)
    private String unitName;

    /*@OneToMany(mappedBy = "unitByUnitId")
    private Collection<EmpPositionEntity> empPositionsByUnitId;

    @ManyToOne
    @JoinColumn(name = "director_id", referencedColumnName = "emp_id")
    private EmployeeEntity employeeByDirectorId;*/

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public Integer getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Integer directorId) {
        this.directorId = directorId;
    }

    public Integer getHigherUnitId() {
        return higherUnitId;
    }

    public void setHigherUnitId(Integer higherUnitId) {
        this.higherUnitId = higherUnitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    /*public Collection<EmpPositionEntity> getEmpPositionsByUnitId() {
        return empPositionsByUnitId;
    }

    public void setEmpPositionsByUnitId(Collection<EmpPositionEntity> empPositionsByUnitId) {
        this.empPositionsByUnitId = empPositionsByUnitId;
    }

    public EmployeeEntity getEmployeeByDirectorId() {
        return employeeByDirectorId;
    }

    public void setEmployeeByDirectorId(EmployeeEntity employeeByDirectorId) {
        this.employeeByDirectorId = employeeByDirectorId;
    }*/
}
