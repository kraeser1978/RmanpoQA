package PageObjects;

import Common.Props;
import Common.RegressionTest;
import PageObjects.PersonalCase.AddPassportPage;
import PageObjects.PersonalCase.StudentPersonalCasePage;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Common.RegressionTest.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.hasWebDriverStarted;

public class LoginPage extends RegressionTest {
    private static Logger logger = Logger.getLogger(LoginPage.class.getSimpleName());
    String userNameXpath = locators.getProperty("user_name");
    String userPasswordXpath = locators.getProperty("user_password");
    String loginButtonXpath = locators.getProperty("login_button");

    public void enterUserName(String userNameValue){ $(By.xpath(userNameXpath)).setValue(userNameValue); }

    public void enterUserPass(String userPassValue){ $(By.xpath(userPasswordXpath)).setValue(userPassValue);}

    public RmanpoMainPage clickLoginButton(){
        $(By.xpath(loginButtonXpath)).click();
        return page(RmanpoMainPage.class);
    }

    public StudentPersonalCasePage main() throws Exception {
        boolean checkIfAppStarted = hasWebDriverStarted();
        if (!checkIfAppStarted){
            logger.log(Level.INFO,"запускаем приложение...");
            SetUp();
            LoginPage loginPage = open(props.appURL(),LoginPage.class);
            logger.log(Level.INFO,"авторизуемся...");
            loginPage.enterUserName(props.userName());
            loginPage.enterUserPass(props.userPass());
            RmanpoMainPage rmanpoMainPage = loginPage.clickLoginButton();
            RmanpoDpoMainPage rmanpoDpoMainPage = rmanpoMainPage.clickRmanpoDpoButton();
            logger.log(Level.INFO,"открываем личное дело слушателя...");
            StudentPersonalCasePage studentPersonalCasePage = rmanpoDpoMainPage.clickContingentDpoLink().clickAllStudentsLink().clickfirstStudentPersonalCaseLink();
        }
        return page(StudentPersonalCasePage.class);
    }
}
