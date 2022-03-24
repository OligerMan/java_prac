package com.example.java_prac.dao;

import com.example.java_prac.dao.implementations.EmpPositionDAOImpl;
import com.example.java_prac.dao.implementations.EmployeeDAOImpl;
import com.example.java_prac.dao.implementations.UnitDAOImpl;
import com.example.java_prac.dao.interfaces.EmpPositionDAO;
import com.example.java_prac.dao.interfaces.EmployeeDAO;
import com.example.java_prac.dao.interfaces.UnitDAO;

public class DAOFactory {

    private static UnitDAO unit_dao = null;
    private static EmployeeDAO emp_dao = null;
    private static EmpPositionDAO emp_pos_dao = null;
    private static DAOFactory instance = null;

    public static synchronized DAOFactory getInstance(){
        if (instance == null){
            instance = new DAOFactory();
        }
        return instance;
    }

    public UnitDAO getUnitDAO(){
        if (unit_dao == null){
            unit_dao = new UnitDAOImpl();
        }
        return unit_dao;
    }

    public EmployeeDAO getEmployeeDAO(){
        if (emp_dao == null){
            emp_dao = new EmployeeDAOImpl();
        }
        return emp_dao;
    }

    public EmpPositionDAO getEmpPositionDAO(){
        if (emp_pos_dao == null){
            emp_pos_dao = new EmpPositionDAOImpl();
        }
        return emp_pos_dao;
    }
}
