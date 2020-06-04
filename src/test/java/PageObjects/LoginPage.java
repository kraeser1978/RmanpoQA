package PageObjects;

import Common.Props;
import PageObjects.PersonalCase.StudentPersonalCasePage;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.io.File;

import static Common.RegressionTest.SetUp;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.WebDriverRunner.hasWebDriverStarted;

public class LoginPage {

    @FindBy(how = How.XPATH, using ="//input[@name='username']")
    private SelenideElement userName;

    @FindBy(how = How.XPATH, using ="//input[@name='password']")
    private SelenideElement userPass;

    @FindBy(how = How.XPATH, using ="//button[@type='submit' and text()='Войти']")
    private SelenideElement loginButton;

    public void enterUserName(String userNameValue){ userName.setValue(userNameValue);}

    public void enterUserPass(String userPassValue){ userPass.setValue(userPassValue);}

    public RmanpoMainPage clickLoginButton(){
        loginButton.click();
        return page(RmanpoMainPage.class);
    }

    public StudentPersonalCasePage main() throws Exception {
        String propsFilePath = System.getenv("Rmanpo_autotest_settings");
        String paramsFile = FileUtils.readFileToString(new File(propsFilePath), "UTF-8");
        Props props = new Props(paramsFile);
        boolean checkIfAppStarted = hasWebDriverStarted();
        if (!checkIfAppStarted){
            SetUp();
            LoginPage loginPage = open(props.appURL(),LoginPage.class);
            loginPage.enterUserName(props.userName());
            loginPage.enterUserPass(props.userPass());
            RmanpoMainPage rmanpoMainPage = loginPage.clickLoginButton();
            RmanpoDpoMainPage rmanpoDpoMainPage = rmanpoMainPage.clickRmanpoDpoButton();
            StudentPersonalCasePage studentPersonalCasePage = rmanpoDpoMainPage.clickContingentDpoLink().clickAllStudentsLink().clickfirstStudentPersonalCaseLink();
        }
        return page(StudentPersonalCasePage.class);
    }
}
