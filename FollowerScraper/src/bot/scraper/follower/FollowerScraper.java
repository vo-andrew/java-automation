package bot.scraper.follower;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class FollowerScraper {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:/Users/Andrew Vo/Desktop/java-automation/chromedriver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        initialSetup(driver);
        scrape(driver);
    }

    private static void initialSetup(WebDriver driver) throws InterruptedException {
        driver.get("https://www.instagram.com/");
        WebElement login = driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/article/div[2]/div[2]/p/a"));
        login.click();
        Thread.sleep(2000);
        WebElement username = driver.findElement(By.name("username"));
        username.sendKeys("iseojun21");
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("dragon");
        WebElement submit = driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/article/div/div[1]/div/form/div[4]/button/div"));
        submit.click();
        WebElement turnOffNotifications = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/button[2]"));
        turnOffNotifications.click();
        WebElement search = driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/nav/div[2]/div/div/div[2]/input"));
        search.sendKeys("anklebreakervo");
        Thread.sleep(3000);
        WebElement profile = driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/nav/div[2]/div/div/div[2]/div[2]/div[2]/div/a[1]/div"));
        profile.click();
        WebElement followersLink = driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/header/section/ul/li[2]/a"));
        followersLink.click();
    }

    private static void scrape(WebDriver driver) {
        List<WebElement> actualFollowers = driver.findElements(By.className("FPmhX"));
        // WebElement followersFrame = driver.findElement(By.xpath("/html/body/div[3]/div/div[2]"));
        for (WebElement follower : actualFollowers) {
            System.out.println(follower.getText());
        }
        // System.out.println(actualFollowers.size());
        driver.quit();
    }

    private static void scrollInsideDiv(WebDriver driver, WebElement scrollArea) {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("window.scrollBy(0,450)", scrollArea);
    }
}
