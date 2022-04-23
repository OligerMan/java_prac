package com.example.java_prac.dao.interfaces;

import com.example.java_prac.dao.implementations.EmployeeDAOImpl;
import com.example.java_prac.entities.EmployeeEntity;

import java.util.List;

public interface EmployeeDAO {

    void addEmployee(EmployeeEntity employee);

    void updateEmployee(EmployeeEntity emp_position);

    void deleteEmployee(EmployeeEntity employee);

    List<EmployeeEntity> getEmployeeListByFilter(EmployeeDAOImpl.Filter filter);

    EmployeeEntity getEmployeeByID(long unit_id);

    EmployeeEntity getEmployeeByName(String employee_name);

    List<EmployeeEntity> getEmployeeListByName(String employee_name);

    EmployeeEntity getEmployeeByCurrentPositionID(long cur_pos_id);
}
