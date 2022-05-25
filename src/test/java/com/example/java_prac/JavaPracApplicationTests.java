package com.example.java_prac;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;


class SeleniumTests {
	int delay_after_add = 1500; // increased for video, 500 is enough
	int base_delay = 1500; // increased for video, 100 is enough

	@Test
	public void addUpdateDeleteDivision() throws InterruptedException {
		ConfigurableApplicationContext ctx = SpringApplication.run(JavaPracApplication.class);

		Thread.sleep(3000);
		ChromeDriver driver = new ChromeDriver();
		driver.get("http://localhost:8080/main");
		driver.findElement(By.linkText("Список служащих")).click();
		driver.findElement(By.linkText("Список подразделений")).click();
		Thread.sleep(3000);
		System.out.println("Test env initialized");

		// Create director for test division
		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("employee_name_add")).click();
		driver.findElement(By.id("employee_name_add")).sendKeys("Тестовый директор");
		driver.findElement(By.id("add_button")).click();
		Thread.sleep(delay_after_add);
		String director_link = driver.findElement(By.linkText("Тестовый директор")).getAttribute("href");
		String director_id = director_link.substring(director_link.indexOf('=') + 1);
		System.out.println("Director created");
		// Create new division
		driver.findElement(By.linkText("Список подразделений")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("division_name_add")).click();
		driver.findElement(By.id("division_name_add")).sendKeys("Тестовое подразделение");
		driver.findElement(By.id("employee_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("employee_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: " + director_id + " Тестовый директор']")).click();
		}
		driver.findElement(By.id("add_button")).click();
		Thread.sleep(delay_after_add);
		String division_link = driver.findElement(By.linkText("Тестовое подразделение")).getAttribute("href");
		String division_id = division_link.substring(division_link.indexOf('=') + 1);
		System.out.println("Division created");

		// Create another director for test division
		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("employee_name_add")).click();
		driver.findElement(By.id("employee_name_add")).sendKeys("Другой тестовый директор");
		driver.findElement(By.id("add_button")).click();
		Thread.sleep(delay_after_add);
		String another_director_link = driver.findElement(By.linkText("Другой тестовый директор")).getAttribute("href");
		String another_director_id = another_director_link.substring(another_director_link.indexOf('=') + 1);
		System.out.println("Another director created");

		// Update data
		driver.findElement(By.linkText("Список подразделений")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Тестовое подразделение")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("employee_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("employee_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: " + another_director_id + " Другой тестовый директор']")).click();
		}
		driver.findElement(By.id("update_button")).click();
		Thread.sleep(delay_after_add);
		System.out.println("Data updated");

		// Delete data and check asserts
		driver.findElement(By.linkText("Список подразделений")).click();
		Thread.sleep(base_delay);
		// check that element exists and link is still unchanged
		Assertions.assertEquals(division_link, driver.findElement(By.linkText("Тестовое подразделение")).getAttribute("href"));
		driver.findElement(By.linkText("Тестовое подразделение")).click();
		Thread.sleep(base_delay);
		// check that data updated
		Assertions.assertEquals(1, driver.findElements(By.linkText("Другой тестовый директор")).size());
		Assertions.assertEquals(0, driver.findElements(By.linkText("Тестовый директор")).size());
		driver.findElement(By.id("delete_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Тестовый директор")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("delete_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Другой тестовый директор")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("delete_button")).click();
		Thread.sleep(delay_after_add);
		driver.close();
		System.out.println("Data deleted");

		ctx.close();
		Thread.sleep(3000);
	}

	@Test
	public void addUpdateDeleteEmployee() throws InterruptedException {
		ConfigurableApplicationContext ctx = SpringApplication.run(JavaPracApplication.class);

		Thread.sleep(3000);
		ChromeDriver driver = new ChromeDriver();
		driver.get("http://localhost:8080/main");
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Список подразделений")).click();
		Thread.sleep(base_delay);
		Thread.sleep(3000);
		System.out.println("Test env initialized");

		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("employee_name_add")).click();
		driver.findElement(By.id("employee_name_add")).sendKeys("Тестовый служащий");
		driver.findElement(By.id("add_button")).click();
		Thread.sleep(delay_after_add);

		driver.findElement(By.linkText("Тестовый служащий")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("home_adr_input")).click();
		driver.findElement(By.id("home_adr_input")).sendKeys("Тестовый адрес");
		driver.findElement(By.id("update_button")).click();
		Thread.sleep(delay_after_add);

		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		Assertions.assertEquals(1, driver.findElements(By.linkText("Тестовый служащий")).size());
		Assertions.assertEquals(1, driver.findElements(By.linkText("Тестовый адрес")).size());
		driver.findElement(By.linkText("Тестовый служащий")).click();
		Assertions.assertEquals("Тестовый адрес", driver.findElement(By.id("home_adr_input")).getAttribute("value"));

		Thread.sleep(base_delay);
		driver.findElement(By.id("delete_button")).click();
		Thread.sleep(delay_after_add);
		driver.close();

		ctx.close();
	}

	@Test
	public void updateEmployeePosition() throws InterruptedException {
		ConfigurableApplicationContext ctx = SpringApplication.run(JavaPracApplication.class);

		Thread.sleep(3000);
		ChromeDriver driver = new ChromeDriver();
		driver.get("http://localhost:8080/main");
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Список подразделений")).click();
		Thread.sleep(base_delay);
		Thread.sleep(3000);
		System.out.println("Test env initialized");

		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("employee_name_add")).click();
		driver.findElement(By.id("employee_name_add")).sendKeys("Директор тестового подразделения");
		driver.findElement(By.id("add_button")).click();
		Thread.sleep(delay_after_add);
		String director_link = driver.findElement(By.linkText("Директор тестового подразделения")).getAttribute("href");
		String director_id = director_link.substring(director_link.indexOf('=') + 1);

		driver.findElement(By.linkText("Список подразделений")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("division_name_add")).click();
		driver.findElement(By.id("division_name_add")).sendKeys("Тестовое подразделение");
		driver.findElement(By.id("employee_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("employee_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: " + director_id + " Директор тестового подразделения']")).click();
		}
		driver.findElement(By.id("add_button")).click();
		Thread.sleep(delay_after_add);
		String division_link = driver.findElement(By.linkText("Тестовое подразделение")).getAttribute("href");
		String division_id = division_link.substring(division_link.indexOf('=') + 1);
		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("employee_name_add")).click();
		driver.findElement(By.id("employee_name_add")).sendKeys("Работник тестового подразделения");
		driver.findElement(By.id("add_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Работник тестового подразделения")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("position_name")).click();
		driver.findElement(By.id("position_name")).sendKeys("Должность1");
		driver.findElement(By.id("position_desc")).click();
		driver.findElement(By.id("position_desc")).sendKeys("Описание должности 1");
		driver.findElement(By.id("division_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("division_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: " + division_id + " Тестовое подразделение']")).click();
		}
		driver.findElement(By.id("button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.id("position_name")).click();
		driver.findElement(By.id("position_name")).clear();
		driver.findElement(By.id("position_name")).sendKeys("Должность2");
		driver.findElement(By.id("position_desc")).click();
		driver.findElement(By.id("position_desc")).clear();
		driver.findElement(By.id("position_desc")).sendKeys("Описание должности 2");
		driver.findElement(By.id("division_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("division_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: " + division_id + " Тестовое подразделение']")).click();
		}
		driver.findElement(By.id("button")).click();
		Thread.sleep(delay_after_add);

		driver.findElement(By.id("update_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Список подразделений")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Тестовое подразделение")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Работник тестового подразделения")).click();
		Thread.sleep(base_delay);
		WebElement table = driver.findElement(By.id("emp_pos_table"));
		// check that added positions exist
		Assertions.assertEquals(1, table.findElements(By.xpath("//*[contains(text(), 'Должность1')]")).size());
		Assertions.assertEquals(1, table.findElements(By.xpath("//*[contains(text(), 'Должность2')]")).size());
		driver.findElement(By.id("delete_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Директор тестового подразделения")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("delete_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Список подразделений")).click();
		Thread.sleep(base_delay);
		driver.close();

		ctx.close();
	}

	@Test
	public void checkEmployeePosHistory() throws InterruptedException {
		ConfigurableApplicationContext ctx = SpringApplication.run(JavaPracApplication.class);

		Thread.sleep(3000);
		ChromeDriver driver = new ChromeDriver();
		driver.get("http://localhost:8080/main");
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Список подразделений")).click();
		Thread.sleep(base_delay);
		Thread.sleep(3000);
		System.out.println("Test env initialized");

		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("employee_name_add")).click();
		driver.findElement(By.id("employee_name_add")).sendKeys("Директор тестового подразделения");
		driver.findElement(By.id("add_button")).click();
		Thread.sleep(delay_after_add);
		String director_link = driver.findElement(By.linkText("Директор тестового подразделения")).getAttribute("href");
		String director_id = director_link.substring(director_link.indexOf('=') + 1);

		driver.findElement(By.linkText("Список подразделений")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("division_name_add")).click();
		driver.findElement(By.id("division_name_add")).sendKeys("Тестовое подразделение");
		driver.findElement(By.id("employee_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("employee_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: " + director_id + " Директор тестового подразделения']")).click();
		}
		driver.findElement(By.id("add_button")).click();
		Thread.sleep(delay_after_add);
		String division1_link = driver.findElement(By.linkText("Тестовое подразделение")).getAttribute("href");
		String division1_id = division1_link.substring(division1_link.indexOf('=') + 1);

		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("employee_name_add")).click();
		driver.findElement(By.id("employee_name_add")).sendKeys("Тестовый служащий");
		driver.findElement(By.id("add_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Тестовый служащий")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("position_name")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("position_name")).sendKeys("Должность1");
		driver.findElement(By.id("position_desc")).sendKeys("Должность1");
		driver.findElement(By.id("division_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("division_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: " + division1_id + " Тестовое подразделение']")).click();
		}
		driver.findElement(By.id("button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.id("position_name")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("position_name")).clear();
		driver.findElement(By.id("position_name")).sendKeys("Должность2");
		driver.findElement(By.id("position_desc")).clear();
		driver.findElement(By.id("position_desc")).sendKeys("Должность2");
		driver.findElement(By.id("division_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("division_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: " + division1_id + " Тестовое подразделение']")).click();
		}
		driver.findElement(By.id("button")).click();
		Thread.sleep(delay_after_add);

		driver.findElement(By.linkText("Список подразделений")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("division_name_add")).click();
		driver.findElement(By.id("division_name_add")).sendKeys("Тестовое подразделение2");
		driver.findElement(By.id("employee_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("employee_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: " + director_id + " Директор тестового подразделения']")).click();
		}
		driver.findElement(By.id("add_button")).click();
		Thread.sleep(delay_after_add);
		String division2_link = driver.findElement(By.linkText("Тестовое подразделение2")).getAttribute("href");
		String division2_id = division2_link.substring(division2_link.indexOf('=') + 1);
		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Тестовый служащий")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("position_name")).click();
		driver.findElement(By.id("position_name")).clear();
		driver.findElement(By.id("position_name")).sendKeys("Должность3");
		driver.findElement(By.id("position_desc")).click();
		driver.findElement(By.id("position_desc")).clear();
		driver.findElement(By.id("position_desc")).sendKeys("Должность3");
		driver.findElement(By.id("division_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("division_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: " + division2_id + " Тестовое подразделение2']")).click();
		}
		driver.findElement(By.id("button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.id("position_desc")).click();
		driver.findElement(By.id("position_desc")).clear();
		driver.findElement(By.id("position_desc")).sendKeys("Должность4");
		driver.findElement(By.id("position_name")).click();
		driver.findElement(By.id("position_name")).clear();
		driver.findElement(By.id("position_name")).sendKeys("Должность4");
		driver.findElement(By.id("button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.id("division_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("division_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: " + division1_id + " Тестовое подразделение']")).click();
		}
		driver.findElement(By.id("position_name")).click();
		driver.findElement(By.id("position_name")).clear();
		driver.findElement(By.id("position_name")).sendKeys("Должность5");
		driver.findElement(By.id("position_desc")).click();
		driver.findElement(By.id("position_desc")).clear();
		driver.findElement(By.id("position_desc")).sendKeys("Должность5");
		driver.findElement(By.id("button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Тестовый служащий")).click();
		Thread.sleep(base_delay);
		// check that added positions exist
		Assertions.assertEquals(1, driver.findElements(By.xpath("//*[contains(text(), 'Должность1')]")).size());
		Assertions.assertEquals(1, driver.findElements(By.xpath("//*[contains(text(), 'Должность2')]")).size());
		Assertions.assertEquals(1, driver.findElements(By.xpath("//*[contains(text(), 'Должность3')]")).size());
		Assertions.assertEquals(1, driver.findElements(By.xpath("//*[contains(text(), 'Должность4')]")).size());
		Assertions.assertEquals(1, driver.findElements(By.xpath("//*[contains(text(), 'Должность5')]")).size());
		driver.findElement(By.id("delete_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Директор тестового подразделения")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("delete_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Список подразделений")).click();
		Thread.sleep(base_delay);
		driver.close();

		ctx.close();
	}

	@Test
	public void findEmployeeWithFilters() throws InterruptedException {
		ConfigurableApplicationContext ctx = SpringApplication.run(JavaPracApplication.class);

		Thread.sleep(3000);
		ChromeDriver driver = new ChromeDriver();
		driver.get("http://localhost:8080/main");
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Список подразделений")).click();
		Thread.sleep(base_delay);
		Thread.sleep(3000);
		System.out.println("Test env initialized");


		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("employee_name_add")).click();
		driver.findElement(By.id("employee_name_add")).clear();
		driver.findElement(By.id("employee_name_add")).sendKeys("Служащий1");
		driver.findElement(By.id("add_button")).click();
		Thread.sleep(delay_after_add);
		String employee_link = driver.findElement(By.linkText("Служащий1")).getAttribute("href");
		String employee_id = employee_link.substring(employee_link.indexOf('=') + 1);
		driver.findElement(By.id("employee_name_add")).click();
		driver.findElement(By.id("employee_name_add")).clear();
		driver.findElement(By.id("employee_name_add")).sendKeys("Служащий2");
		driver.findElement(By.id("add_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.id("employee_name_add")).click();
		driver.findElement(By.id("employee_name_add")).clear();
		driver.findElement(By.id("employee_name_add")).sendKeys("Служащий3");
		driver.findElement(By.id("add_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.id("employee_name_add")).click();
		driver.findElement(By.id("employee_name_add")).clear();
		driver.findElement(By.id("employee_name_add")).sendKeys("Служащий4");
		driver.findElement(By.id("add_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.id("employee_name_add")).click();
		driver.findElement(By.id("employee_name_add")).clear();
		driver.findElement(By.id("employee_name_add")).sendKeys("Служащий5");
		driver.findElement(By.id("add_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Служащий1")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("home_adr_input")).click();
		driver.findElement(By.id("home_adr_input")).sendKeys("РФ, Москва");
		driver.findElement(By.id("update_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Служащий2")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("home_adr_input")).click();
		driver.findElement(By.id("home_adr_input")).sendKeys("РФ, Санкт-Петербург");
		driver.findElement(By.id("update_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Служащий3")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("home_adr_input")).click();
		driver.findElement(By.id("home_adr_input")).sendKeys("РФ, Калининград");
		driver.findElement(By.id("update_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Служащий4")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("home_adr_input")).click();
		driver.findElement(By.id("home_adr_input")).sendKeys("США, Нью-Йорк");
		driver.findElement(By.id("update_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Служащий5")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("home_adr_input")).click();
		driver.findElement(By.id("home_adr_input")).sendKeys("Германия, Берлин");
		driver.findElement(By.id("update_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		Thread.sleep(delay_after_add);
		driver.findElement(By.id("employee_address")).click();
		driver.findElement(By.id("employee_address")).sendKeys("РФ");
		driver.findElement(By.id("button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Служащий1")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("employee_address")).click();
		driver.findElement(By.id("employee_address")).sendKeys("РФ");
		driver.findElement(By.id("button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Служащий2")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("employee_address")).click();
		driver.findElement(By.id("employee_address")).sendKeys("РФ");
		driver.findElement(By.id("button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Служащий3")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("employee_address")).click();
		driver.findElement(By.id("employee_address")).sendKeys("Берлин");
		driver.findElement(By.id("button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Служащий5")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("employee_address")).click();
		driver.findElement(By.id("employee_address")).sendKeys("США, Нью-Йорк");
		driver.findElement(By.id("button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Служащий4")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("employee_id")).click();
		driver.findElement(By.id("employee_id")).sendKeys(employee_id);
		driver.findElement(By.id("button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Служащий1")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Служащий1")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("delete_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Служащий2")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("delete_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Служащий3")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("delete_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Служащий4")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("delete_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Служащий5")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("delete_button")).click();
		Thread.sleep(delay_after_add);
		driver.close();
		// Asserts not needed because if smth is wrong, test will fail because of incorrect redirect

		ctx.close();
	}

	@Test
	public void getSubdivisionStructure() throws InterruptedException {
		ConfigurableApplicationContext ctx = SpringApplication.run(JavaPracApplication.class);

		Thread.sleep(3000);
		ChromeDriver driver = new ChromeDriver();
		driver.get("http://localhost:8080/main");
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Список подразделений")).click();
		Thread.sleep(base_delay);
		Thread.sleep(3000);
		System.out.println("Test env initialized");

		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("employee_name_add")).click();
		driver.findElement(By.id("employee_name_add")).sendKeys("Тестовый директор");
		driver.findElement(By.id("add_button")).click();
		Thread.sleep(delay_after_add);
		String director_link = driver.findElement(By.linkText("Тестовый директор")).getAttribute("href");
		String director_id = director_link.substring(director_link.indexOf('=') + 1);
		driver.findElement(By.linkText("Список подразделений")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("employee_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("employee_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: " + director_id + " Тестовый директор']")).click();
		}
		driver.findElement(By.id("division_name_add")).click();
		driver.findElement(By.id("division_name_add")).clear();
		driver.findElement(By.id("division_name_add")).sendKeys("Подразделение1_Уровень1");
		driver.findElement(By.id("add_button")).click();
		Thread.sleep(delay_after_add);
		String division1_link = driver.findElement(By.linkText("Подразделение1_Уровень1")).getAttribute("href");
		String division1_id = division1_link.substring(division1_link.indexOf('=') + 1);
		driver.findElement(By.id("division_name_add")).click();
		driver.findElement(By.id("division_name_add")).clear();
		driver.findElement(By.id("division_name_add")).sendKeys("Подразделение2_Уровень2");
		driver.findElement(By.id("add_button")).click();
		Thread.sleep(delay_after_add);
		String division2_link = driver.findElement(By.linkText("Подразделение2_Уровень2")).getAttribute("href");
		String division2_id = division2_link.substring(division2_link.indexOf('=') + 1);
		driver.findElement(By.id("division_name_add")).click();
		driver.findElement(By.id("division_name_add")).clear();
		driver.findElement(By.id("division_name_add")).sendKeys("Подразделение3_Уровень3");
		driver.findElement(By.id("add_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.id("division_name_add")).click();
		driver.findElement(By.id("division_name_add")).clear();
		driver.findElement(By.id("division_name_add")).sendKeys("Подразделение4_Уровень2");
		driver.findElement(By.id("add_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.id("division_name_add")).click();
		driver.findElement(By.id("division_name_add")).clear();
		driver.findElement(By.id("division_name_add")).sendKeys("Подразделение5_Уровень2");
		driver.findElement(By.id("add_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Подразделение2_Уровень2")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("employee_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("employee_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: " + director_id + " Тестовый директор']")).click();
		}
		driver.findElement(By.id("division_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("division_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: " + division1_id + " Подразделение1_Уровень1']")).click();
		}
		driver.findElement(By.id("update_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Список подразделений")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Подразделение3_Уровень3")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("employee_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("employee_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: " + director_id + " Тестовый директор']")).click();
		}
		driver.findElement(By.id("division_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("division_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: " + division2_id + " Подразделение2_Уровень2']")).click();
		}
		driver.findElement(By.id("update_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Список подразделений")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Подразделение4_Уровень2")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("employee_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("employee_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: " + director_id + " Тестовый директор']")).click();
		}
		driver.findElement(By.id("division_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("division_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: " + division1_id + " Подразделение1_Уровень1']")).click();
		}
		driver.findElement(By.id("update_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Список подразделений")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Подразделение5_Уровень2")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("employee_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("employee_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: " + director_id + " Тестовый директор']")).click();
		}
		driver.findElement(By.id("division_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("division_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: " + division1_id + " Подразделение1_Уровень1']")).click();
		}
		driver.findElement(By.id("update_button")).click();
		Thread.sleep(delay_after_add);
		driver.findElement(By.linkText("Список подразделений")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Подразделение1_Уровень1")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Подразделение2_Уровень2")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Подразделение3_Уровень3")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Подразделение2_Уровень2")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Подразделение1_Уровень1")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Подразделение4_Уровень2")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Подразделение1_Уровень1")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Подразделение5_Уровень2")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Подразделение1_Уровень1")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Список подразделений")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Список служащих")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.linkText("Тестовый директор")).click();
		Thread.sleep(base_delay);
		driver.findElement(By.id("delete_button")).click();
		Thread.sleep(delay_after_add);
		driver.close();
		// Asserts not needed because if smth is wrong, test will fail because of incorrect redirect

		ctx.close();
	}
}
