package bot.scraper.follower;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class FollowerScraper {
    public static void main(String[] args) throws InterruptedException, IOException {
        System.setProperty("webdriver.chrome.driver", "C:/Users/Andrew Vo/Desktop/java-automation/chromedriver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Scanner userInput = new Scanner(System.in);
        System.out.println("Instagram username:");
        String instagramUsername = userInput.nextLine();
        System.out.println("Instagram password:");
        String instagramPassword = userInput.nextLine();
        System.out.println("Type in the Instagram username of the person you would like to have a list of followers from:");
        String instagramUser = userInput.nextLine();
        initialSetup(driver, instagramUsername, instagramPassword);
        List<String> followers = getUserFollowers(driver, instagramUser);
        createExcelSheet(followers);
        driver.quit();
    }

    private static void initialSetup(WebDriver driver, String userlogin, String userpass) throws InterruptedException {
        System.out.println("Fetching webpage...");
        driver.get("https://www.instagram.com/");
        WebElement login = driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/article/div[2]/div[2]/p/a"));
        login.click();
        Thread.sleep(3000);
        WebElement username = driver.findElement(By.name("username"));
        username.sendKeys(userlogin);
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys(userpass);
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
            System.out.println(numberOfFollowersInList + " followers loaded.");
            Thread.sleep(2000);
        }
        List<String> followers = new ArrayList<>();
        for (WebElement user : followersList.findElements(By.cssSelector("body > div.RnEpo.Yx5HN > div > div.isgrP > ul > div > li"))) {
            String userLink = user.findElement(By.cssSelector("a")).getAttribute("href");
            followers.add(userLink);
            if (followers.size() == maxFollowers) {
                break;
            }
        }
        return followers;
    }

    private static void createExcelSheet(List<String> users) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Instagram Followers");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Instagram Link");
        row.createCell(1).setCellValue("Instagram Username");
        int length = users.size();
        for (int i = 0; i < length; i += 1) {
            row = sheet.createRow(i + 1);
            String hrefLink = users.get(i);
            row.createCell(0).setCellValue(hrefLink);
            row.createCell(1).setCellValue(hrefLink.replaceAll("(https://www.instagram.com/)|(\\/$)", ""));
        }
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        workbook.write(new FileOutputStream("followers.xls"));
        workbook.close();
        System.out.println("Excel sheet created!");
    }
}
