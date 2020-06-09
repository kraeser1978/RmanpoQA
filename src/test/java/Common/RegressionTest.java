package Common;

import PageObjects.PersonalCase.AddPassportPage;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;

public class RegressionTest {
    private static Logger logger = Logger.getLogger(RegressionTest.class.getSimpleName());
    protected static WebDriver driver;
    static FileHandler fh = null;
    public static Properties dataInput = new Properties();
    public static Set<String> fieldNames;
    public static Properties locators = new Properties();
    public static Set<String> locatorCodes;
    public static Props props;

    public ArrayList<String> getFieldNames(ArrayList<String> titles){
        String fieldNamesXpath = locators.getProperty("get_input_field_name_template");
        int fieldsCount = $$(By.xpath(fieldNamesXpath)).size();
        for (int i= 0; i < fieldsCount; i++){
            titles.add($$(By.xpath(fieldNamesXpath)).get(i).getText());
        }
        return titles;
    }

    public ArrayList<String> getComboBoxNames(ArrayList<String> titles){
        String comboBoxNamesXpath = locators.getProperty("get_combobox_name_template");
        int fieldsCount = $$(By.xpath(comboBoxNamesXpath)).size();
        for (int i= 0; i < fieldsCount; i++){
            titles.add($$(By.xpath(comboBoxNamesXpath)).get(i).getText());
        }
        return titles;
    }

    public static void SetUp() throws Exception {
        logger.log(Level.INFO,"считываем параметры проекта из properties файлов...");
        //считываем индивидуальные параметры
        String propsFilePath = System.getenv("RmanpoQA_personal_settings");
        String paramsFile = FileUtils.readFileToString(new File(propsFilePath), "UTF-8");
        props = new Props(paramsFile);
        //считываем локаторы
        String locatorsFilePath = System.getenv("RmanpoQA_personal_case_locators");
        String locatorsText = FileUtils.readFileToString(new File(locatorsFilePath), "UTF-8");
        locators.load(new StringReader(locatorsText));
        locatorCodes = locators.stringPropertyNames();
        //считываем датасет
        String inputDataFilePath = System.getenv("RmanpoQA_personal_case_inputDataFile");
        String inputDataText = FileUtils.readFileToString(new File(inputDataFilePath), "UTF-8");
        dataInput.load(new StringReader(inputDataText));
        fieldNames = dataInput.stringPropertyNames();
        //задаем режим логирования сообщений в лог файл
        logInit(props.logFilePath());
        //задаем путь к файлам скриншотов с ошибками в ходе выполнения тестов
        Configuration.reportsFolder = props.screenshotsFolder();
        logger.log(Level.INFO,"прибиваем chromedriver.exe процесс, если он остался от предыдущей сессии");
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
        logger.log(Level.INFO,"запускаем Хром...");
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
