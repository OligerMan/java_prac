package com.example.java_prac.dao.interfaces;

import com.example.java_prac.dao.implementations.EmpPositionDAOImpl;
import com.example.java_prac.entities.EmpPositionEntity;
import com.example.java_prac.entities.EmployeeEntity;

import java.util.List;

public interface EmpPositionDAO {

    void addEmpPosition(EmpPositionEntity emp_position);

    void deleteEmpPosition(EmpPositionEntity emp_position);

    List<EmployeeEntity> getEmpPositionListByFilter(EmpPositionDAOImpl.Filter filter);

    EmpPositionEntity getEmpPositionByID(long unit_id);

    List<EmpPositionEntity> getEmpPositionListByUnitID(long unit_id);

    List<EmpPositionEntity> getEmpPositionListByEmpID(long emp_id);
}
