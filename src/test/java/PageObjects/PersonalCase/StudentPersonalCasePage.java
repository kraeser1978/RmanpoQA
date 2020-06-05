package PageObjects.PersonalCase;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class StudentPersonalCasePage {
    String editCase = "(//button[@title='Изменить личное дело'])[1]";
    String cancelCaseEdit = "//button[@title='Отменить изменения']";
    String saveCase = "(//button[@title='Сохранить изменения'])[1]";
//    String campus = "//span[text()='Общежитие']//parent::div/following-sibling::div/span/input[contains(@class,'combobox-input')]";
    String campus = "//span[text()='Общежитие']//parent::div/following-sibling::div/span/input";
    String family = "//span[text()='Фамилия']//parent::div/following-sibling::div/input";

    public StudentPersonalCasePage(){
        switchTo().window(1);
        //ждем окончания загрузки страницы
        $(By.xpath(editCase)).waitUntil(Condition.enabled,7000);
    }

    public String getFamilyName(){
        String familyNameValue = $(By.xpath(family)).getAttribute("value");
        return familyNameValue;
    }

    public StudentPersonalCasePage setFamilyName(String familyName){
        $(By.xpath(family)).setValue(familyName);
        System.out.println("задаем поле Фамилия = " + familyName);
        return this;
    }

    public StudentPersonalCasePage isCampusAdded(String expectedCampusName){
        String actualCampusName = $(By.xpath(campus)).getAttribute("value");
        if (actualCampusName.contains(expectedCampusName)) System.out.println("Значение поля Общежитие совпало с ранее введенным");
        else {
            System.out.println("ОШИБКА: значение поля Общежитие отличается от ожидаемого:");
            System.out.println("Ожидаемое = " + expectedCampusName + " ,фактическое = " + actualCampusName);
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
        $(By.xpath(editCase)).click();
        return this;
    }

    public StudentPersonalCasePage clickCancelCaseEditButton(){
        $(By.xpath(cancelCaseEdit)).click();
        //ждем, когда поле станет закрытым для редактирования
        $(By.xpath(campus)).shouldHave(Condition.disabled);
        return this;
    }

    public StudentPersonalCasePage clickSaveCaseButton(){
        $(By.xpath(saveCase)).click();
        return this;
    }

    public String getCampus(){
        String campusValue = $(By.xpath(campus)).getAttribute("value");
        return campusValue ;
    }

    public StudentPersonalCasePage setCampus(String campusName){
        $(By.xpath(campus)).setValue(campusName);
        System.out.println("задаем поле Общежитие = " + campusName);
        return this;
    }


}
