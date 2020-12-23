package Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;

public class BaseTest{

    private WebDriver driver;
    public static int step_count=1;
    ExcelProcess excel = new ExcelProcess();

    @BeforeSuite
    public void beforeSuite() throws IOException {

        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        excel.excelCreation();
        try{
            driver.navigate().to("https://www.grootan.com/");
            driver.manage().window().maximize();
            excel.updateReport("Navigated to the URL https://www.grootan.com/ and maximized window", "Pass", "");
            step_count++;
        }catch (Exception e){
            excel.updateReport("Navigated to the URL https://www.grootan.com/ and maximized window", "Fail", "");
            step_count++;
        }
    }

    @AfterSuite
    public void afterSuite() {
        try{
            if(null != driver) {
                driver.close();
                driver.quit();
                excel.updateReport("Closed the browser", "Pass","");
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}