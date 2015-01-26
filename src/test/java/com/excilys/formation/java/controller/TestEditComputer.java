package com.excilys.formation.java.controller;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


public class TestEditComputer {
  private WebDriver driver;

  @Before
  public void setUp(){
    driver = new FirefoxDriver();
  }

  @Test
  public void editComputer() {
    driver.get("http://localhost:8080/computer-database/editComputer?id=7");

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
