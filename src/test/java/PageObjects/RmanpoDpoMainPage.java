package PageObjects;

import PageObjects.PersonalCase.StudentPersonalCasePage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.codeborne.selenide.Selenide.page;

public class RmanpoDpoMainPage {

    @FindBy(how = How.XPATH, using = "//span[text()='Контингент ДПО']")
    private SelenideElement ContingentDpoLink;

    @FindBy(how = How.XPATH, using = "//span[text()='Все слушатели']")
    private SelenideElement AllStudentsLink;

    @FindBy(how = How.XPATH, using = "//table/tbody[contains(@id,'-rows')]/tr[1]/td[1]/div/div/div/a[@title='Открыть форму личного дела']")
    private SelenideElement firstStudentPersonalCaseLink;

    public RmanpoDpoMainPage clickContingentDpoLink(){
        ContingentDpoLink.click();
        return this;
    }

    public RmanpoDpoMainPage clickAllStudentsLink(){
        AllStudentsLink.shouldBe(Condition.visible);
        AllStudentsLink.shouldBe(Condition.enabled).click();
        return this;
    }

    public StudentPersonalCasePage clickfirstStudentPersonalCaseLink(){
        firstStudentPersonalCaseLink.shouldBe(Condition.enabled).click();
        return page(StudentPersonalCasePage.class);
    }
}
