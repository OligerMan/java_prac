package com.example.java_prac.dao.implementations;

import com.example.java_prac.JavaPracApplication;
import com.example.java_prac.dao.DAOFactory;
import com.example.java_prac.dao.interfaces.EmpPositionDAO;
import com.example.java_prac.dao.interfaces.EmployeeDAO;
import com.example.java_prac.dao.interfaces.UnitDAO;
import com.example.java_prac.entities.EmpPositionEntity;
import com.example.java_prac.entities.EmployeeEntity;
import com.example.java_prac.entities.UnitEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;

import java.sql.Timestamp;
import java.util.List;

class DAOImplTest {

    @Test
    void addAndDelete() {
        SpringApplication.run(JavaPracApplication.class);

        UnitDAO u_dao = DAOFactory.getInstance().getUnitDAO();
        EmployeeDAO e_dao = DAOFactory.getInstance().getEmployeeDAO();
        EmpPositionDAO p_dao = DAOFactory.getInstance().getEmpPositionDAO();

        long now = System.currentTimeMillis();

        // create test data
        EmployeeEntity emp1 = new EmployeeEntity(0, "RF, Moscow", "Ivan", 2000000001);
        EmployeeEntity emp2 = new EmployeeEntity(0, "RF, Kaliningrad", "Oleg", 2000000002);
        EmployeeEntity emp3 = new EmployeeEntity(0, "RF, Saint Petersburg", "Daria", 2000000003);
        e_dao.addEmployee(emp1);
        e_dao.addEmployee(emp2);
        e_dao.addEmployee(emp3);

        UnitEntity unit1 = new UnitEntity(0, emp2.getEmpId(), -1, "Unit1");
        u_dao.addUnit(unit1);

        EmpPositionEntity emp_pos1 = new EmpPositionEntity
                (
                        0,
                        unit1.getUnitId(),
                        emp1.getEmpId(),
                        "Director",
                        "Director of Unit1",
                        new Timestamp(now),
                        new Timestamp(now)
                );
        EmpPositionEntity emp_pos2 = new EmpPositionEntity
                (
                        0,
                        unit1.getUnitId(),
                        emp2.getEmpId(),
                        "Worker",
                        "Worker of Unit1",
                        new Timestamp(now),
                        new Timestamp(now)
                );
        EmpPositionEntity emp_pos3 = new EmpPositionEntity
                (
                        0,
                        unit1.getUnitId(),
                        emp3.getEmpId(),
                        "Worker",
                        "Worker of Unit1",
                        new Timestamp(now),
                        new Timestamp(now)
                );
        p_dao.addEmpPosition(emp_pos1);
        p_dao.addEmpPosition(emp_pos2);
        p_dao.addEmpPosition(emp_pos3);

        // assert tests
        EmployeeEntity get_emp = e_dao.getEmployeeByName("Ivan");
        Assertions.assertEquals(get_emp.getHomeAddress(), "RF, Moscow");

        UnitEntity get_unit = u_dao.getUnitByDirectorID(get_emp.getEmpId());
        Assertions.assertNull(get_unit);

        UnitEntity get_unit2 = u_dao.getUnitByDirectorID(emp2.getEmpId());
        Assertions.assertEquals(get_unit2.getUnitName(), "Unit1");

        List<EmpPositionEntity> get_pos = p_dao.getEmpPositionListByUnitID(get_unit2.getUnitId());
        Assertions.assertEquals(get_pos.size(), 3);

        // delete test data
        p_dao.deleteEmpPosition(emp_pos1);
        p_dao.deleteEmpPosition(emp_pos2);
        p_dao.deleteEmpPosition(emp_pos3);
        u_dao.deleteUnit(unit1);
        e_dao.deleteEmployee(emp1);
        e_dao.deleteEmployee(emp2);
        e_dao.deleteEmployee(emp3);
    }
}