# java-automation

This repository holds a collection of programs written in Java to automate certain tasks.  

## Technologies used in this repository  

### Selenium WebDriver  

This is the main dependency I will be using to automate tasks in the browser. You will need to download Selenium's WebDriver for Java at https://www.seleniumhq.org/download/. In addition, programs that I write will only use the Google Chrome browser. To automate programs in Chrome, you will need to download Selenium's ChromeDriver at https://sites.google.com/a/chromium.org/chromedriver/.  

Before starting any of the automation scripts, you will need to do two things:  
1. Add the JAR files from the extracted WebDriver folder into the build path of the project. 
2. Set the path of your ChromeDriver instance to where you downloaded your ChromeDriver executable file. You can do this in Java by typing
```
System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");
```
**before** you create your instance of ChromeDriver.  

Once you finish these two things, you are ready to start using my Java scripts.

### Apache POI

This API is used to read and write excel sheets from Java. You can download Apache POI's libraries at https://poi.apache.org/download.html in the binary distribution section. After extracting the zip file, make sure to add these two JAR files into your build path:  
1. poi-4.1.0.jar (or whatever your current version is)  
2. poi-ooxml-4.1.0.jar (or whatever your current version is)  

After doing this, you will be able to create excel files from my scripts!
