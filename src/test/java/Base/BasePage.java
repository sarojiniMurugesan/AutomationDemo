package Base;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static Base.BaseTest.step_count;

public class BasePage extends ExcelProcess{

    private static final int TIMEOUT = 1000;
    protected WebDriver driver;
    private WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
         wait = new WebDriverWait(driver,10);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIMEOUT), this);
    }

    public String currentTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyyHHmmssSSS");
        Date date = new Date();
        return (formatter.format(date)).toString();
    }

    public void reportUpdateStep(String desc, String status) {
        try {
            String time = System.getProperty("user.dir")+"//Reports//ReportImages_"+(formatter.format(date)).toString()+"//" + currentTime() + ".png";
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(time));
            updateReport(desc, status, time);
            step_count++;
        }catch (IOException e){
            System.out.println(e);
        }
    }

    protected void delay(int value){
        try {
            Thread.sleep(value*1000);
            updateReport("wait for "+value+"s", "Pass","");
            step_count++;
        }catch (Exception e){
            try {
                updateReport("wait for " + value + "s error " + e, "Fail", "");
                step_count++;
            }catch (Exception e1){
                System.out.println(e1);
            }
        }
    }

    protected void waitForElementToAppear(String locator, String element) {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(locators(locator, element))));
        reportUpdateStep("waitForElementToAppear "+locator+": "+element, "Pass");
    }

    protected void click(String locator, String element){
        try {
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(locators(locator, element))));
            driver.findElement(locators(locator, element)).click();
            reportUpdateStep("Clicked "+locator+": "+element, "Pass");
        }catch (Exception e){
            reportUpdateStep("Clicked "+locator+": "+element+" error: "+e, "Fail");
        }
    }

    protected String getText(String locator, String element) {
        String value="";
        try {
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(locators(locator, element))));
            value = driver.findElement(locators(locator, element)).getText();
            reportUpdateStep("Read text "+locator+": "+element, "Pass");
        }catch (Exception e){
            reportUpdateStep("Read text "+locator+": "+element+" error: "+e, "Fail");
        }
        return value;
    }

    protected void text(String locator, String element, String value) {
        try {
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(locators(locator, element))));
            driver.findElement(locators(locator, element)).sendKeys(value);
            reportUpdateStep("Entered text '"+value+"' in"+locator+": "+element, "Pass");
        }catch (Exception e){
            reportUpdateStep("Entered text '"+value+"' in"+locator+": "+element+" error: "+e, "Fail");
        }
    }
    protected List<WebElement> findElements(String locator, String element) {
        List<WebElement> value = null;
        try {
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(locators(locator, element))));
             value = driver.findElements(locators(locator, element));
            reportUpdateStep("Returned all element in "+locator+": "+element, "Pass");

        }catch (Exception e){
            reportUpdateStep("Returned all element in "+locator+": "+element+" error: "+e, "Fail");
        }
        return value;
    }

    protected void navigate(String url){
     try{
         driver.navigate().to(url);
        reportUpdateStep("Navigate to the URL "+url, "Pass");

    }catch (Exception e){
        reportUpdateStep("Navigate to the URL "+url+" error: "+e, "Fail");
    }
    }

    protected void maximizeWindow(){
        try {
            driver.manage().window().maximize();
            reportUpdateStep("Maximized Window ", "Pass");
        }catch (Exception e){
            reportUpdateStep("Maximized Window- error"+e, "Fail");
        }
    }

    protected void closeBrowser(){
        try{
            if(null != driver) {
                driver.close();
                driver.quit();
                updateReport("Closed the browser", "Pass","");
            }
        }catch (Exception e){}
    }

    protected void screenshot(String filePath){
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File(filePath));
            updateReport("Took Screenshot", "Pass", "");
            step_count++;
        } catch (IOException e) {
            try {
                updateReport("Took Screenshot error: " + e, "Fail", "");
                step_count++;
            }catch (IOException e1){
                System.out.println(e1);
            }
        }
    }

    public By locators(String locator, String element) {
        switch (locator) {
            case "xpath":
                return By.xpath(element);
            case "id":
                return By.id(element);
            default:
                return By.linkText(element);
        }
    }
}