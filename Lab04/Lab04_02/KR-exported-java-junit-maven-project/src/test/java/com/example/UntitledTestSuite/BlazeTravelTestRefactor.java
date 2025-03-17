package com.example.UntitledTestSuite;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import io.github.bonigarcia.seljup.SeleniumJupiter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SeleniumJupiter.class) // Extensão do Selenium para JUnit 5
public class BlazeTravelTestRefactor {

    @Test
    void testBlazeTravel(WebDriver driver) {
        driver.get("https://blazedemo.com/");

        // Seleciona cidade de origem e destino
        new Select(driver.findElement(By.name("fromPort"))).selectByVisibleText("Philadelphia");
        new Select(driver.findElement(By.name("toPort"))).selectByVisibleText("London");
        driver.findElement(By.xpath("//input[@value='Find Flights']")).click();

        // Seleciona um voo e prossegue
        driver.findElement(By.xpath("//tr[5]/td/input")).click();

        // Preenche o formulário de compra
        driver.findElement(By.id("inputName")).sendKeys("Diogo Costa");
        driver.findElement(By.id("address")).sendKeys("5th Avenue");
        driver.findElement(By.id("city")).sendKeys("New York");
        driver.findElement(By.id("state")).sendKeys("NY");
        driver.findElement(By.id("zipCode")).sendKeys("123456");
        new Select(driver.findElement(By.id("cardType"))).selectByVisibleText("American Express");
        driver.findElement(By.id("creditCardNumber")).sendKeys("123456789");
        driver.findElement(By.id("creditCardYear")).sendKeys("2025");
        driver.findElement(By.id("nameOnCard")).sendKeys("Diogo Costa");

        // Finaliza a compra
        driver.findElement(By.xpath("//input[@value='Purchase Flight']")).click();

        // Valida se a compra foi concluída verificando o título da página
        assertEquals("BlazeDemo Confirmation", driver.getTitle());
    }
}