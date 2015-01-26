package com.excilys.formation.java.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.excilys.formation.java.persistence.MockDaoFactory;

public class TestEditComputer {
  private WebDriver driver;

  @Before
  public void setUp() throws SQLException {
    driver = new FirefoxDriver();
    
    MockDaoFactory dao = MockDaoFactory.getInstance();
    Connection conn = dao.getConnection();

    Statement stmt = conn.createStatement();
    stmt.execute("Truncate computer");

    stmt.execute("insert into computer (id,name,introduced,discontinued,company_id) values (  1,'MacBook Pro 15.4 inch',null,null,1);");
    stmt.execute("insert into computer (id,name,introduced,discontinued,company_id) values (  2,'MacBook Pro','2006-01-10',null,1);");
    dao.closeConnection(conn, stmt, null);
  }

  @Test
  public void editComputer() {
    driver.get("http://localhost:8080/computer-database/editComputer?id=2");

    WebElement name = driver.findElement(By.name("name"));
    name.sendKeys("NameTest");

    WebElement introduced = driver.findElement(By.name("introduced"));
    introduced.sendKeys("2014-02-03");

    WebElement discontinued = driver.findElement(By.name("discontinued"));
    discontinued.sendKeys("2015-03-06");

    WebElement company = driver.findElement(By.name("companyId"));
    List<WebElement> companies = company.findElements(By.tagName("option"));

    WebElement companyOption = companies.get(3);
    companyOption.click();

    driver.findElement(By.id("submit")).click();
  }

  @After
  public void tearDown() {
    driver.close();
  }
}
