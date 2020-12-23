package Pages;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import Base.BasePage;
import org.openqa.selenium.WebElement;
import org.sikuli.script.*;
import java.io.*;
import java.util.List;

import static Base.BaseTest.step_count;

public class GrootanPage extends BasePage {

    public GrootanPage(WebDriver driver) {
        super(driver);

    }

    public static String page_name="";

    public void sections(String locator, String element) {
        click(locator,element);
        page_name=getText(locator, element);
    }

    public void juniorEngineerName() throws IOException {
        try {
            updateReport("Listing all Junior Engineers in JuniorEngineer sheet ", "", "");
        } catch (IOException e) {
            e.printStackTrace();
        }

        click("xpath", "//li[contains(@class,'primary nav-item')]/a[text()='Team']");
        waitForElementToAppear("xpath", "//li[contains(@class,'primary nav-item')]/a[text()='Team']");
        List<WebElement> juniors = findElements("xpath", "//h5[text()='Junior Engineer'][@class='member-role']/..//h3");
        String[] junior_engineers = new String[juniors.size()];
        for(int i = 0; i< juniors.size(); i++){
            junior_engineers[i] = juniors.get(i).getText();
            updateSheet(junior_engineers[i]);
        }
    }

    public void ss() throws IOException {
        WebElement drpdwn = driver.findElement(By.xpath("//li[contains(@class,'primary nav-item')]/a[text()='Home']"));
        File f = drpdwn.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(f, new File("D://screenshots.png"));
    }

    public void snapOfPages(String folder) {
        try {
            updateReport("Navigating to all menus and saved the snap in "+folder, "", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = System.getProperty("user.dir")+"\\"+folder+"\\";

        sections("xpath", "//li[contains(@class,'primary nav-item')]/a[text()='Home']");
        waitForElementToAppear("xpath", "//li[contains(@class,'primary nav-item')]/a[text()='Home']");
        screenshot(path+page_name+".png");

        sections("xpath", "//li[contains(@class,'primary nav-item')]/a[text()='Services']");
        waitForElementToAppear("xpath", "//li[contains(@class,'primary nav-item')]/a[text()='Services']");
        screenshot(path+page_name+".png");

        sections("xpath", "//li[contains(@class,'primary nav-item')]/a[text()='Open Source']");
        waitForElementToAppear("xpath", "//li[contains(@class,'primary nav-item')]/a[text()='Open Source']");
        screenshot(path+page_name+".png");

        delay(2);

        sections("xpath", "//li[contains(@class,'primary nav-item')]/a[text()='Blog']");
        waitForElementToAppear("xpath", "//li[contains(@class,'primary nav-item')]/a[text()='Blog']");
        screenshot(path+page_name+".png");

        sections("xpath", "//li[contains(@class,'primary nav-item')]/a[text()='Team']");
        waitForElementToAppear("xpath", "//li[contains(@class,'primary nav-item')]/a[text()='Team']");
        screenshot(path+page_name+".png");

        sections("xpath", "//li[contains(@class,'primary nav-item')]/a[text()='Careers']");
        waitForElementToAppear("xpath", "//li[contains(@class,'primary nav-item')]/a[text()='Careers']");
        screenshot(path+page_name+".png");

        sections("xpath", "//li[contains(@class,'primary nav-item')]/a[text()='Contact Us']");
        waitForElementToAppear("xpath", "//li[contains(@class,'primary nav-item')]/a[text()='Contact Us']");
        screenshot(path+page_name+".png");

    }

    public void compateImages(String folder1, String folder2) throws IOException {
        updateReport("Comparing screenshots between "+folder1+" and "+folder2, "", "");

        File[] file = new File(System.getProperty("user.dir")+"\\"+folder1+"\\").listFiles();
        int count = file.length;
        String[] filename = new String[count];
        int j=0;
        for(int i=0;i<count;i++){
            if(file[i].getName().contains(".png")){
                filename[j] = file[i].getName();
                j++;
            }
        }
        System.out.println(filename.length);
        for(int k=0;k<filename.length;k++) {
            Pattern drag = new Pattern(System.getProperty("user.dir")+"\\"+folder2+"\\" + filename[k]);
            Finder ob = new Finder(System.getProperty("user.dir")+"\\"+folder1+"\\" + filename[k]);
            ob.find(drag);
            int counter = 0;
            Match objMatch;
            while (ob.hasNext()) {
                objMatch = ob.next();
                counter++;
            }
            if (counter != 0) {
                updateReport("Comparing "+filename[k]+" between "+folder1+" and "+folder2, "Pass", "");
                step_count++;
            } else {
                updateReport("Comparing "+filename[k]+" between "+folder1+" and "+folder2, "Fail", "");
                step_count++;
            }
        }
    }
}