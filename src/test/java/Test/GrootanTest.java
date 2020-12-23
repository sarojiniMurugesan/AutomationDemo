package Test;

import org.testng.annotations.Test;
import Base.BaseTest;
import Pages.GrootanPage;

import java.io.IOException;

public class GrootanTest extends BaseTest {

    @Test
    public void GrootanSiteTest() throws IOException {
        GrootanPage page = new GrootanPage(getDriver());
        page.snapOfPages("folder1");
        page.snapOfPages("folder2");
        page.compareImages("folder1", "folder2");
        page.juniorEngineerName();
        page.comparingCTOAndHRImages();
    }
}
