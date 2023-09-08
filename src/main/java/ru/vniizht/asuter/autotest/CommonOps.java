package ru.vniizht.asuter.autotest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class CommonOps {

    private static final Logger logger = LoggerFactory.getLogger(CommonOps.class);

    public static void setupAndLogin(WebDriver driver) {
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        logger.info("Авторизация");
        driver.get("http://asuter-dev.vniizht.lan/login");
        driver.findElement(By.id("username")).click();
        driver.findElement(By.id("username")).sendKeys("autotest");
        driver.findElement(By.id("password")).sendKeys("AutoTest_113");
        driver.findElement(By.id("button-login")).click();
    }

    public static void wait(int seconds) {
        logger.info(String.format("Пауза %d секунд", seconds));
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
