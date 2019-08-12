package bot.scraper.follower;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FollowerScraper {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:/Users/Andrew Vo/Desktop/java-automation/chromedriver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        initialSetup(driver);
        getUserFollowers(driver, "anklebreakervo");
    }

    private static void initialSetup(WebDriver driver) throws InterruptedException {
        driver.get("https://www.instagram.com/");
        WebElement login = driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/article/div[2]/div[2]/p/a"));
        login.click();
        Thread.sleep(3000);
        WebElement username = driver.findElement(By.name("username"));
        username.sendKeys("iseojun21");
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("dragon");
        WebElement submit = driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/article/div/div[1]/div/form/div[4]/button/div"));
        submit.click();
        WebElement turnOffNotifications = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]/button[2]"));
        turnOffNotifications.click();
    }

    private static List<String> getUserFollowers(WebDriver driver, String username) throws InterruptedException {
        driver.get("https://www.instagram.com/" + username);
        int maxFollowers = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/header/section/ul/li[2]/a/span")).getAttribute("title"));
        WebElement followersLink = driver.findElement(By.cssSelector("#react-root > section > main > div > header > section > ul > li:nth-child(2) > a"));
        followersLink.click();
        Thread.sleep(2000);
        WebElement followersList = driver.findElement(By.cssSelector("div[role='dialog'] ul"));
        int numberOfFollowersInList = followersList.findElements(By.cssSelector("body > div.RnEpo.Yx5HN > div > div.isgrP > ul > div > li")).size();
        followersList.click();
        Actions actionChain = new Actions(driver);
        while (numberOfFollowersInList < maxFollowers) {
            actionChain.sendKeys(Keys.SPACE).perform();
            numberOfFollowersInList = followersList.findElements(By.cssSelector("body > div.RnEpo.Yx5HN > div > div.isgrP > ul > div > li")).size();
            System.out.println(numberOfFollowersInList);
            Thread.sleep(2000);
        }
        List<String> followers = new ArrayList<>();
        for (WebElement user : followersList.findElements(By.cssSelector("body > div.RnEpo.Yx5HN > div > div.isgrP > ul > div > li"))) {
            String userLink = user.findElement(By.cssSelector("a")).getAttribute("href");
            System.out.println(userLink);
            followers.add(userLink);
            if (followers.size() == maxFollowers) {
                break;
            }
        }
        return followers;
    }
}
