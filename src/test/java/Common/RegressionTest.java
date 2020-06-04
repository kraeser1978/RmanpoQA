package Common;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;

public class RegressionTest {
    protected static WebDriver driver;
    static FileHandler fh = null;

    public static void SetUp() throws Exception {
        String propsFilePath = System.getenv("Rmanpo_autotest_settings");
        String paramsFile = FileUtils.readFileToString(new File(propsFilePath), "UTF-8");
        Props props = new Props(paramsFile);
        //задаем режим логирования сообщений в лог файл
        logInit(props.logFilePath());
        //задаем путь к файлам скриншотов с ошибками в ходе выполнения тестов
        Configuration.reportsFolder = props.screenshotsFolder();
        //прибываем процесс, если он остался от предыдущей сессии
        Process process = Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
        process.waitFor();
        process.destroy();
        //задаем опции запуска браузера
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-extenstions");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--incognito");
        options.addArguments("--disable-default-apps");
        options.addArguments("--enable-precise-memory-info");
        System.setProperty("webdriver.chrome.driver",props.driverPath());
        driver = new ChromeDriver(options);
        WebDriverRunner.setWebDriver(driver);
    }

    public static void tearDown(){
        if (!driver.getWindowHandle().equals("")) {
            fh.flush();
            fh.close();
            driver.close();
            closeWebDriver();
        }
    }

    public static void logInit(String lofFileName){
        try {
            fh = new FileHandler(lofFileName,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger log = Logger.getLogger("");
        fh.setFormatter(new SimpleFormatter());
        log.addHandler(fh);
        log.setLevel(Level.CONFIG);
    }

}
