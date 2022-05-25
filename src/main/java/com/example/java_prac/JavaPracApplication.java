package com.example.java_prac;

import com.example.java_prac.dao.DAOFactory;
import com.example.java_prac.dao.interfaces.EmpPositionDAO;
import com.example.java_prac.dao.interfaces.EmployeeDAO;
import com.example.java_prac.dao.interfaces.UnitDAO;
import com.example.java_prac.entities.EmpPositionEntity;
import com.example.java_prac.entities.EmployeeEntity;
import com.example.java_prac.entities.UnitEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Timestamp;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class JavaPracApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaPracApplication.class, args);

		/*UnitDAO u_dao = DAOFactory.getInstance().getUnitDAO();
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

		int randint = ThreadLocalRandom.current().nextInt(0, 1000 + 1);
		UnitEntity unit1 = new UnitEntity(0, emp2.getEmpId(), -1, "Unit" + randint);
		u_dao.addUnit(unit1);

		EmpPositionEntity emp_pos1 = new EmpPositionEntity
				(
						0,
						unit1.getUnitId(),
						emp1.getEmpId(),
						"Director",
						"Director of Unit" + randint,
						new Timestamp(now),
						new Timestamp(now)
				);
		EmpPositionEntity emp_pos2 = new EmpPositionEntity
				(
						0,
						unit1.getUnitId(),
						emp2.getEmpId(),
						"Worker",
						"Worker of Unit" + randint,
						new Timestamp(now),
						new Timestamp(now)
				);
		EmpPositionEntity emp_pos3 = new EmpPositionEntity
				(
						0,
						unit1.getUnitId(),
						emp3.getEmpId(),
						"Worker",
						"Worker of Unit" + randint,
						new Timestamp(now),
						new Timestamp(now)
				);
		p_dao.addEmpPosition(emp_pos1);
		p_dao.addEmpPosition(emp_pos2);
		p_dao.addEmpPosition(emp_pos3);*/
	}

}
