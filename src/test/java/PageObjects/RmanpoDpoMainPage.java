package PageObjects;

import Common.RegressionTest;
import PageObjects.PersonalCase.StudentPersonalCasePage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class RmanpoDpoMainPage extends RegressionTest {
    private static Logger logger = Logger.getLogger(RmanpoDpoMainPage.class.getSimpleName());
    String contingentDPOLink = locators.getProperty("menu_item_template").replace("''","'Контингент ДПО'");
    String allStudentsLink = locators.getProperty("menu_item_template").replace("''","'Все слушатели'");
    String firstStudentPersonalCaseLink = locators.getProperty("firstStudentPersonalCaseLink");

    public RmanpoDpoMainPage clickContingentDpoLink(){
        $(By.xpath(contingentDPOLink)).shouldBe(Condition.enabled).click();
        return this;
    }

    public RmanpoDpoMainPage clickAllStudentsLink(){
        $(By.xpath(allStudentsLink)).shouldBe(Condition.visible);
        $(By.xpath(allStudentsLink)).shouldBe(Condition.enabled).click();
        return this;
    }

    public StudentPersonalCasePage clickfirstStudentPersonalCaseLink(){
        $(By.xpath(firstStudentPersonalCaseLink)).shouldBe(Condition.enabled).click();
        return page(StudentPersonalCasePage.class);
    }
}
