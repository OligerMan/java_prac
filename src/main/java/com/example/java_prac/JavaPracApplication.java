package com.example.java_prac;

import com.example.java_prac.dao.DAOFactory;
import com.example.java_prac.entities.EmployeeEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaPracApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaPracApplication.class, args);

		EmployeeEntity employee = DAOFactory.getInstance().getEmployeeDAO().getEmployeeByID(11);
		if (employee == null) {
			System.err.println("Employee not found");
		} else {
			System.out.println("Employee name: " + employee.getEmpName());
			System.out.println("Employee home address: " + employee.getHomeAddress());
		}
	}

}
