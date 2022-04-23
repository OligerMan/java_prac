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
        int get_emp_id = get_emp.getEmpId();
        int get_emp_pos_id = get_emp.getCurrentPositionId();
        Assertions.assertEquals(get_emp.getHomeAddress(), "RF, Moscow");
        get_emp.setEmpName("Voldemort");
        get_emp.setHomeAddress("GB, London");
        e_dao.updateEmployee(get_emp);
        Assertions.assertEquals(get_emp.getEmpName(), "Voldemort");
        get_emp = e_dao.getEmployeeByID(get_emp_id);
        Assertions.assertEquals(get_emp.getEmpName(), "Voldemort");
        Assertions.assertEquals(get_emp.getHomeAddress(), "GB, London");
        Assertions.assertEquals(get_emp.getCurrentPositionId(), get_emp_pos_id);
        get_emp = e_dao.getEmployeeByCurrentPositionID(get_emp_pos_id);
        Assertions.assertEquals(get_emp.getEmpName(), "Voldemort");
        EmployeeDAOImpl.Filter e_dao_filter = new EmployeeDAOImpl.Filter();
        e_dao_filter.setId(get_emp_id);
        get_emp = e_dao.getEmployeeListByFilter(e_dao_filter).get(0);
        Assertions.assertEquals(get_emp.getEmpName(), "Voldemort");


        UnitEntity get_unit = u_dao.getUnitByDirectorID(get_emp.getEmpId());
        Assertions.assertNull(get_unit);

        UnitEntity get_unit2 = u_dao.getUnitByDirectorID(emp2.getEmpId());
        int unit_id = get_unit2.getUnitId();
        int higher_unit_id = get_unit2.getHigherUnitId();
        int unit_dir_id = get_unit2.getDirectorId();
        Assertions.assertEquals(get_unit2.getUnitName(), "Unit1");
        get_unit2 = u_dao.getUnitByID(unit_id);
        Assertions.assertEquals(get_unit2.getUnitName(), "Unit1");
        get_unit2 = u_dao.getUnitListByHigherUnitID(higher_unit_id).get(0);
        Assertions.assertEquals(get_unit2.getUnitName(), "Unit1");
        UnitDAOImpl.Filter u_dao_filter = new UnitDAOImpl.Filter();
        u_dao_filter.setDirectorId(unit_dir_id);
        get_unit2 = u_dao.getUnitListByFilter(u_dao_filter).get(0);
        Assertions.assertEquals(get_unit2.getUnitName(), "Unit1");
        get_unit2.setUnitName("Army of Evil");
        u_dao.updateUnit(get_unit2);
        Assertions.assertEquals(get_unit2.getUnitName(), "Army of Evil");

        List<EmpPositionEntity> get_pos = p_dao.getEmpPositionListByUnitID(get_unit2.getUnitId());
        int pos_id = get_pos.get(0).getPosId();
        String p_name = get_pos.get(0).getPosName();
        Timestamp p_st_time = get_pos.get(0).getStartTimestamp();
        String p_desc = get_pos.get(0).getPosDesc();
        Assertions.assertEquals(get_pos.size(), 3);
        EmpPositionDAOImpl.Filter p_dao_filter = new EmpPositionDAOImpl.Filter();
        p_dao_filter.setPosName(p_name);
        get_pos = p_dao.getEmpPositionListByFilter(p_dao_filter);
        Assertions.assertEquals(get_pos.size(), 1);
        EmpPositionEntity get_pos_ent = p_dao.getEmpPositionByID(pos_id);
        Assertions.assertEquals(get_pos_ent.getStartTimestamp(), p_st_time);
        Assertions.assertEquals(get_pos_ent.getPosDesc(), p_desc);
        get_pos = p_dao.getEmpPositionListByEmpID(get_emp.getEmpId());
        Assertions.assertEquals(get_pos.size(), 1);

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