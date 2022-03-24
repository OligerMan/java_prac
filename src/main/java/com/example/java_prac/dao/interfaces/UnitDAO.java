package com.example.java_prac.dao.interfaces;

import com.example.java_prac.dao.implementations.UnitDAOImpl;
import com.example.java_prac.entities.EmployeeEntity;
import com.example.java_prac.entities.UnitEntity;

import java.util.List;

public interface UnitDAO {
    void addUnit(UnitEntity unit);

    void deleteUnit(UnitEntity unit);

    List<EmployeeEntity> getUnitListByFilter(UnitDAOImpl.Filter filter);

    UnitEntity getUnitByID(long unit_id);

    UnitEntity getUnitByDirectorID(long director_id);

    List<UnitEntity> getUnitListByDirectorID(long director_id);

    List<UnitEntity> getUnitListByHigherUnitID(long higher_unit_id);
}
