package com.example.UntitledTestSuite;

import java.time.Duration;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;

import static org.assertj.core.api.Fail.fail;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.apache.commons.io.FileUtils;
import java.io.File;

public class BlazeTravelTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  JavascriptExecutor js;
  @Before
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver", "");
    driver = new ChromeDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
    js = (JavascriptExecutor) driver;
  }

  @Test
  public void testBlazeTravel() throws Exception {
    driver.get("https://blazedemo.com/");
    new Select(driver.findElement(By.name("fromPort"))).selectByVisibleText("Philadelphia");
    new Select(driver.findElement(By.name("toPort"))).selectByVisibleText("London");
    driver.findElement(By.xpath("//input[@value='Find Flights']")).click();
    driver.findElement(By.xpath("//tr[5]/td/input")).click();
    driver.findElement(By.id("inputName")).click();
    driver.findElement(By.id("inputName")).clear();
    driver.findElement(By.id("inputName")).sendKeys("Diogo");
    driver.findElement(By.id("inputName")).click();
    driver.findElement(By.id("inputName")).clear();
    driver.findElement(By.id("inputName")).sendKeys("Diogo Costa");
    driver.findElement(By.id("address")).click();
    driver.findElement(By.id("address")).clear();
    driver.findElement(By.id("address")).sendKeys("5th Avenue");
    driver.findElement(By.id("city")).clear();
    driver.findElement(By.id("city")).sendKeys("New York");
    driver.findElement(By.id("state")).clear();
    driver.findElement(By.id("state")).sendKeys("NY");
    driver.findElement(By.id("zipCode")).clear();
    driver.findElement(By.id("zipCode")).sendKeys("123456");
    new Select(driver.findElement(By.id("cardType"))).selectByVisibleText("American Express");
    driver.findElement(By.id("creditCardNumber")).click();
    driver.findElement(By.id("creditCardNumber")).clear();
    driver.findElement(By.id("creditCardNumber")).sendKeys("123456789");
    driver.findElement(By.id("creditCardYear")).click();
    driver.findElement(By.id("creditCardYear")).clear();
    driver.findElement(By.id("creditCardYear")).sendKeys("2025");
    driver.findElement(By.id("nameOnCard")).click();
    driver.findElement(By.id("nameOnCard")).clear();
    driver.findElement(By.id("nameOnCard")).sendKeys("Diogo Costa");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Name on Card'])[1]/following::label[1]")).click();
    driver.findElement(By.xpath("//input[@value='Purchase Flight']")).click();
    assertEquals(driver.getTitle(), "BlazeDemo Confirmation");
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
