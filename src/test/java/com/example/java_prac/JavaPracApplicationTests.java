package com.example.java_prac;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;

class SeleniumTests {

	@Test
	void divisionsSeleniumTest() throws InterruptedException {
		ConfigurableApplicationContext ctx = SpringApplication.run(JavaPracApplication.class);

		Thread.sleep(3000);
		ChromeDriver driver = new ChromeDriver();
		driver.get("http://localhost:8080/divisions");
		Thread.sleep(3000);

		List<WebElement> rows = driver.findElements(By.tagName("tr"));
		List<String> text_info = new ArrayList<String>();
		List<String> link_info = new ArrayList<String>();
		for (WebElement row : rows) {
			List<WebElement> elements = row.findElements(By.tagName("td"));
			if (elements.size() != 0) {
				WebElement name_elem = elements.get(1).findElement(By.tagName("a"));
				System.out.println(name_elem.getText());
				System.out.println(name_elem.getAttribute("href"));
				text_info.add(name_elem.getText());
				link_info.add(name_elem.getAttribute("href"));
			}
		}

		for (int i = 0; i < text_info.size(); i++) {
			driver.get(link_info.get(i));
			Thread.sleep(500);
			Assertions.assertEquals(driver.findElement(By.id("div_name_input")).getAttribute("value"), text_info.get(i));
		}

		ctx.close();
	}


	@Test
	void divisionsDeleteSeleniumTest() throws InterruptedException {
		ConfigurableApplicationContext ctx = SpringApplication.run(JavaPracApplication.class);

		Thread.sleep(3000);
		ChromeDriver driver = new ChromeDriver();
		driver.get("http://localhost:8080/divisions");
		Thread.sleep(3000);

		List<WebElement> rows = driver.findElements(By.tagName("tr"));
		List<String> text_info = new ArrayList<String>();
		List<String> link_info = new ArrayList<String>();
		for (WebElement row : rows) {
			List<WebElement> elements = row.findElements(By.tagName("td"));
			if (elements.size() != 0) {
				WebElement name_elem = elements.get(1).findElement(By.tagName("a"));
				System.out.println(name_elem.getText());
				System.out.println(name_elem.getAttribute("href"));
				text_info.add(name_elem.getText());
				link_info.add(name_elem.getAttribute("href"));
			}
		}

		driver.get(link_info.get(0));
		Thread.sleep(1000);
		driver.findElement(By.id("update_button")).click();
		Thread.sleep(500);
		driver.findElement(By.id("delete_button")).click();
		Thread.sleep(500);
		driver.get("http://localhost:8080/divisions");
		Thread.sleep(3000);
		Assertions.assertEquals(text_info.size(), driver.findElements(By.tagName("tr")).size());

		ctx.close();
	}

	@Test
	void employeesSeleniumTest() throws InterruptedException {
		ConfigurableApplicationContext ctx = SpringApplication.run(JavaPracApplication.class);

		Thread.sleep(3000);
		ChromeDriver driver = new ChromeDriver();
		driver.get("http://localhost:8080/employees");
		Thread.sleep(3000);

		List<WebElement> rows = driver.findElements(By.tagName("tr"));
		List<String> text_info = new ArrayList<String>();
		List<String> link_info = new ArrayList<String>();
		for (WebElement row : rows) {
			List<WebElement> elements = row.findElements(By.tagName("td"));
			if (elements.size() != 0) {
				WebElement name_elem = elements.get(1).findElement(By.tagName("a"));
				System.out.println(name_elem.getText());
				System.out.println(name_elem.getAttribute("href"));
				text_info.add(name_elem.getText());
				link_info.add(name_elem.getAttribute("href"));
			}
		}

		for (int i = 0; i < text_info.size(); i++) {
			driver.get(link_info.get(i));
			Thread.sleep(500);
			Assertions.assertEquals(driver.findElement(By.id("emp_name_input")).getAttribute("value"), text_info.get(i));
		}
		ctx.close();
	}


	@Test
	void employeesDeleteSeleniumTest() throws InterruptedException {
		ConfigurableApplicationContext ctx = SpringApplication.run(JavaPracApplication.class);

		Thread.sleep(3000);
		ChromeDriver driver = new ChromeDriver();
		driver.get("http://localhost:8080/employees");
		Thread.sleep(3000);

		List<WebElement> rows = driver.findElements(By.tagName("tr"));
		List<String> text_info = new ArrayList<String>();
		List<String> link_info = new ArrayList<String>();
		for (WebElement row : rows) {
			List<WebElement> elements = row.findElements(By.tagName("td"));
			if (elements.size() != 0) {
				WebElement name_elem = elements.get(1).findElement(By.tagName("a"));
				System.out.println(name_elem.getText());
				System.out.println(name_elem.getAttribute("href"));
				text_info.add(name_elem.getText());
				link_info.add(name_elem.getAttribute("href"));
			}
		}


		driver.get(link_info.get(0));
		Thread.sleep(1000);
		driver.findElement(By.id("update_button")).click();
		Thread.sleep(500);
		driver.findElement(By.id("delete_button")).click();
		Thread.sleep(500);
		driver.get("http://localhost:8080/employees");
		Thread.sleep(3000);
		Assertions.assertEquals(text_info.size(), driver.findElements(By.tagName("tr")).size());

		ctx.close();
	}
}
