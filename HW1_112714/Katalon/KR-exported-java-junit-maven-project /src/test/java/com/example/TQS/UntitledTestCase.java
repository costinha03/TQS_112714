package com.example.TQS;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.apache.commons.io.FileUtils;
import java.io.File;

public class UntitledTestCase {
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
  public void testUntitledTestCase() throws Exception {
    //ERROR: Caught exception [unknown command []]
    driver.findElement(By.xpath("//div[@id='root']/div/main/div/div[2]/div[2]/div[4]/div[2]/button")).click();
    driver.findElement(By.xpath("//body")).click();
    driver.findElement(By.xpath("//ul[@id='«rg»']/li")).click();
    driver.findElement(By.xpath("//body")).click();
    driver.findElement(By.xpath("//ul[@id='«rh»']/li[2]")).click();
    driver.findElement(By.id("«ri»")).click();
    driver.findElement(By.id("«ri»")).clear();
    driver.findElement(By.id("«ri»")).sendKeys("123");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Cancel'])[1]/following::button[1]")).click();
    driver.findElement(By.id("restaurant-tab-2")).click();
    driver.findElement(By.xpath("//div[@id='root']/div/main/div/div[2]/div[2]/div/div[2]/button")).click();
    driver.findElement(By.xpath("//body")).click();
    driver.findElement(By.xpath("//ul[@id='«rm»']/li[2]")).click();
    driver.findElement(By.xpath("//body")).click();
    driver.findElement(By.xpath("//ul[@id='«rn»']/li")).click();
    driver.findElement(By.id("«ro»")).click();
    driver.findElement(By.id("«ro»")).clear();
    driver.findElement(By.id("«ro»")).sendKeys("123");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Cancel'])[1]/following::button[1]")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Total Price:'])[1]/following::button[1]")).click();
    driver.findElement(By.linkText("Check Reservations")).click();
    driver.findElement(By.id("«rs»")).click();
    driver.findElement(By.id("«rs»")).clear();
    driver.findElement(By.id("«rs»")).sendKeys("123");
    driver.findElement(By.xpath("//button[@type='button']")).click();
    driver.findElement(By.xpath("//div[@id='root']/div/main/div/div[2]/ul/li[2]/button[2]")).click();
    assertEquals("Reservation canceled successfully!", closeAlertAndGetItsText());
    driver.findElement(By.xpath("//button[@type='button']")).click();
    driver.findElement(By.xpath("//div[@id='root']/div/main/div/div[2]/ul/li/button")).click();
    assertEquals("Failed to check in: Check-in is only allowed on the reservation date", closeAlertAndGetItsText());
    driver.findElement(By.linkText("Restaurants")).click();
    driver.findElement(By.xpath("//div[@id='root']/div/main/div/div[2]/div[2]/div/div[2]/button")).click();
    driver.findElement(By.xpath("//body")).click();
    driver.findElement(By.xpath("//ul[@id='«r19»']/li[2]")).click();
    driver.findElement(By.xpath("//body")).click();
    driver.findElement(By.xpath("//ul[@id='«r1a»']/li")).click();
    driver.findElement(By.id("«r1b»")).click();
    driver.findElement(By.id("«r1b»")).clear();
    driver.findElement(By.id("«r1b»")).sendKeys("123");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Cancel'])[1]/following::button[1]")).click();
    driver.findElement(By.linkText("Staff Verify")).click();
    driver.findElement(By.xpath("//div[@id='root']/div/main/div/header/div/div[2]/div/button[3]")).click();
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
