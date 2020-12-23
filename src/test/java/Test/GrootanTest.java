package Test;

import org.testng.annotations.Test;
import Base.BaseTest;
import Pages.GrootanPage;

import java.io.IOException;

public class GrootanTest extends BaseTest {

    @Test
    public void homepageTests() throws IOException {
        GrootanPage page = new GrootanPage(getDriver());
        page.snapOfPages("folder1");
        page.snapOfPages("folder2");
        page.compateImages("folder1", "folder2");
        page.juniorEngineerName();
//        page.ss();
    }
}
