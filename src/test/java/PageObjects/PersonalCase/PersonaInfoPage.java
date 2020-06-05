package PageObjects.PersonalCase;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.$;

public class PersonaInfoPage extends StudentPersonalCasePage {
    private static Logger logger = Logger.getLogger(PersonaInfoPage.class.getSimpleName());
    String countryResidence = "//span[text()='Гражданство']//parent::div/following-sibling::div/span/input[contains(@class,'combobox-input')]";
    String position = "//span[text()='Должность']//parent::div/following-sibling::div/input";
    String cancelEditButton1 = "(//button[@title='Отменить изменения'])[1]";
    String cancelEditButton2 = "(//button[@title='Отменить изменения'])[2]";
    String saveCaseButton2 = "(//button[@title='Сохранить изменения'])[2]";
    String editCaseButton2 = "(//button[@title='Изменить личное дело'])[2]";

    public PersonaInfoPage(){
        //ждем окончания загрузки страницы
        $(By.xpath(editCase)).waitUntil(Condition.enabled,7000);
    }

    public PersonaInfoPage setCountryResidence(String countryName){
        clickEditPersonalCaseButton();
        $(By.xpath(countryResidence)).setValue(countryName);
        return this;
    }

    public PersonaInfoPage clickSaveCaseButton2(){
        $(By.xpath(saveCaseButton2)).shouldBe(Condition.enabled).click();
        return this;
    }

    public PersonaInfoPage clickEditPersonalCaseButton2(){
        $(By.xpath(editCaseButton2)).shouldBe(Condition.enabled).click();
        return this;
    }

    public PersonaInfoPage clickCancelCaseEditButton1() throws InterruptedException {
        $(By.xpath(cancelEditButton1)).shouldBe(Condition.enabled).click();
        //ждем, когда поле станет закрытым для редактирования
        $(By.xpath(countryResidence)).shouldHave(Condition.disabled);
//        Thread.sleep(300);
        return this;
    }

    public PersonaInfoPage clickCancelCaseEditButton2() throws InterruptedException {
        $(By.xpath(cancelEditButton2)).shouldBe(Condition.enabled).click();
        //ждем, когда поле станет закрытым для редактирования
        $(By.xpath(position)).shouldHave(Condition.disabled);
//        Thread.sleep(300);
        return this;
    }

}
