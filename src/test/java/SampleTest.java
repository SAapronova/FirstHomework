import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

@Listeners(ExecutionListener.class)
public class SampleTest {

    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(SampleTest.class);

    @BeforeTest
    @Parameters("browser")
    public void setup(@Optional("chrome") String browser) throws Exception {

        //Check if parameter passed as 'chrome'
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            logger.info("Started with chrome");
        }
        //Check if parameter passed from TestNG is 'firefox'
        else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            logger.info("Started with firefox");
        } else {

            throw new Exception("Browser is not correct");
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }


    @Test
    public void openPage() {
        driver.get("https://otus.ru/");
        logger.info("Page opened");

    }

//    @Test //расскоментить для того,чтобы тест упал и в метрики записался FAIL
//    public void openPage2() {
//        driver.get("https://otus.ru/");
//        driver.findElement(By.id("tt"));
//        logger.info("Fail test");
//    }

    @AfterTest
    public void setDown() {
        driver.close();
        logger.info("Browser closed");

    }

}

