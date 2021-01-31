package test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class first {

    WebDriver driver;
    WebDriverWait wait;
    SoftAssert a = new SoftAssert();

    @BeforeSuite
    public void driverInitialize()
    {
        System.setProperty("webdriver.chrome.driver","C:\\Shazeen\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 5);
        driver.manage().window().maximize();
        driver.get("https://demo.thingsboard.io/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username-input")));
        ChromeOptions co = new ChromeOptions();
        co.addArguments("--disable-notifications");

    }

    @Test(enabled = false)
    public void acceptCookies()
    {
        driver.findElement(By.id("u_0_h")).click();
    }

    @Test(dataProvider = "getData")
    public void signIn(String username, String password) {
        driver.findElement(By.id("username-input")).sendKeys(username);
        driver.findElement(By.id("password-input")).sendKeys(password);
        driver.findElement(By.xpath("//div[@class = 'tb-action-button']/button")).click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.querySelector('mat-card.mat-card.mat-focus-indicator').scrollTop = 50");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("tb-snack-bar-component[class *= 'ng-tns-c165-']")));
        System.out.println(driver.findElement(By.cssSelector("tb-snack-bar-component[class *= 'ng-tns-c165-']  div div")).getText());
        driver.findElement(By.cssSelector("tb-snack-bar-component[class *= 'ng-tns-c165-'] button")).click();
        driver.findElement(By.id("username-input")).clear();
        driver.findElement(By.id("password-input")).clear();
        a.assertAll();
    }

    @Test(priority=1)
    public void forgotPassword()
    {
        driver.findElement(By.cssSelector("button[class*='tb-reset-password']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("mat-headline")));
        a.assertEquals(driver.findElement(By.className("mat-headline")).getText(), "Request Password Reset");
        driver.findElement(By.cssSelector("input[id*='mat-input']")).sendKeys("abs");
        driver.findElement(By.cssSelector("button[type ='submit']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("mat-error[id*='mat-error']")));
        a.assertEquals(driver.findElement(By.cssSelector("mat-error[id*='mat-error']")).getText(), "Invalid email format.");
        driver.findElement(By.cssSelector("button[class *= 'mat-primary']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[class='tb-login-message']")));
        a.assertEquals(driver.findElement(By.cssSelector("span[class='tb-login-message']")).getText(),"Log in to see ThingsBoard in action.");
        a.assertAll();
    }

    @DataProvider
    public Object[][] getData()
    {
        Object[][] data = new Object[3][2];
        //first iteration
        data[0][0] = "shaz@dfsd.com";
        data[0][1] = "mah";

        //second iteration
        data[1][0] = "asb@sdfs.com";
        data[1][1] = "fss";

        //third iteration
        data[2][0] = "the@te.com";
        data[2][1]  = "wer";

        return data;
    }


}
