package PageObjects.PersonalCase;

import Tests.DemoTest;
import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.*;

public class StudentPersonalCasePage {
    private static Logger logger = Logger.getLogger(StudentPersonalCasePage.class.getSimpleName());
    String editCase = "(//button[@title='Изменить личное дело'])[1]";
    String cancelCaseEdit = "//button[@title='Отменить изменения']";
    String saveCase = "(//button[@title='Сохранить изменения'])[1]";
    String campus = "//span[text()='Общежитие']//parent::div/following-sibling::div/span/input";
    String family = "//span[text()='Фамилия']//parent::div/following-sibling::div/input";

    public StudentPersonalCasePage(){
        switchTo().window(1);
        //ждем окончания загрузки страницы
        $(By.xpath(editCase)).waitUntil(Condition.enabled,7000);
    }

    public String getFieldValue(String fieldName){
        String fieldValueXpath = family.replace("Фамилия",fieldName);
        String fieldValue = $(By.xpath(fieldValueXpath)).getAttribute("value");
        return fieldValue;
    }

    public String getComboBoxValue(String fieldName){
        String comboBoxValueXpath = campus.replace("Общежитие",fieldName);
        String comboBoxValue = $(By.xpath(comboBoxValueXpath)).getAttribute("value");
        return comboBoxValue;
    }

    public StudentPersonalCasePage setFieldValue(String fieldName, String fieldValue){
        String fieldValueXpath = family.replace("Фамилия",fieldName);
        $(By.xpath(fieldValueXpath)).shouldBe(Condition.enabled).setValue(fieldValue);
        logger.log(Level.INFO,"задаем поле " + fieldName + " = " + fieldValue);
        return this;
    }

    public StudentPersonalCasePage setComboBoxValue(String fieldName, String fieldValue){
        String comboBoxValueXpath = campus.replace("Общежитие",fieldName);
        $(By.xpath(comboBoxValueXpath)).shouldBe(Condition.enabled).setValue(fieldValue);
        logger.log(Level.INFO,"задаем поле " + fieldName + " = " + fieldValue);
        return this;
    }

    private void compareFieldValues(String fieldName, String actualValue, String expectedValue){
        if (actualValue.contains(expectedValue)) logger.log(Level.INFO,"Значение поля " + fieldName + " совпало с ранее введенным");
        else {
            logger.log(Level.SEVERE,"ОШИБКА: значение поля " + fieldName + " отличается от ожидаемого:");
            logger.log(Level.INFO,"Ожидаемое = " + expectedValue + " , фактическое = " + actualValue);
        }
    }

    public StudentPersonalCasePage isFieldValueAdded(String fieldName, String expectedValue){
        String actualValue = getFieldValue(fieldName);
        compareFieldValues(fieldName, actualValue, expectedValue);
        return this;
    }

    public StudentPersonalCasePage isComboBoxValueAdded(String fieldName, String expectedValue){
        String actualValue = getComboBoxValue(fieldName);
        compareFieldValues(fieldName, actualValue, expectedValue);
        return this;
    }

    public StudentPersonalCasePage isCampusAdded(String expectedCampusName){
        String actualCampusName = $(By.xpath(campus)).getAttribute("value");
        if (actualCampusName.contains(expectedCampusName)) System.out.println("Значение поля Общежитие совпало с ранее введенным");
        else {
            logger.log(Level.SEVERE,"ОШИБКА: значение поля Общежитие отличается от ожидаемого:");
            logger.log(Level.INFO,"Ожидаемое = " + expectedCampusName + " , фактическое = " + actualCampusName);
        }
        return this;
    }

    public PersonaInfoPage switchToPersonaInfoTab(){
        $(By.xpath("//span[text()='Персональная информация']")).click();
        return page(PersonaInfoPage.class);
    }

    public IdentityDocsPage switchToIdentityDocsTab(){
        $(By.xpath("//span[text()='Документы, удостоверяющие личность']")).click();
        return page(IdentityDocsPage.class);
    }

    public EducationDocsPage switchToEducationDocsTab(){
        $(By.xpath("//span[text()='Документы об образовании']")).click();
        return page(EducationDocsPage.class);
    }

    public StudentPersonalCasePage clickEditPersonalCaseButton(){
        $(By.xpath(editCase)).shouldBe(Condition.enabled).click();
        return this;
    }

    public StudentPersonalCasePage clickCancelCaseEditButton(){
        $(By.xpath(cancelCaseEdit)).shouldBe(Condition.enabled).click();
        //ждем, когда поле станет закрытым для редактирования
        $(By.xpath(campus)).shouldHave(Condition.disabled);
        return this;
    }

    public StudentPersonalCasePage clickSaveCaseButton(){
        $(By.xpath(saveCase)).shouldBe(Condition.enabled).click();
        return this;
    }

    public String getCampus(){
        String campusValue = $(By.xpath(campus)).getAttribute("value");
        return campusValue ;
    }

    public StudentPersonalCasePage setCampus(String campusName){
        $(By.xpath(campus)).setValue(campusName);
        logger.log(Level.INFO,"задаем поле Общежитие = " + campusName);
        return this;
    }
}
