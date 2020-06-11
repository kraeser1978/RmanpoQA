package PageObjects.PersonalCase;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import java.util.logging.Logger;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PersonaInfoPage extends StudentPersonalCasePage {
    private static Logger logger = Logger.getLogger(PersonaInfoPage.class.getSimpleName());
    String countryResidence = locators.getProperty("set_combobox_value_template").replace("''","'Гражданство'");
    String position = locators.getProperty("set_input_field_value_template").replace("''","'Должность'");;
    String cancelEditButton1 = locators.getProperty("click_sequential_button_template").replace("''","'Отменить изменения'");
    String cancelEditButton2 = locators.getProperty("click_sequential_button_template")
        .replace("''","'Отменить изменения'").replace("[1]","[2]");
    String saveCaseButton2 = locators.getProperty("click_sequential_button_template")
        .replace("''","'Сохранить изменения'").replace("[1]","[2]");
    String editCaseButton2 = locators.getProperty("click_sequential_button_template")
        .replace("''","'Изменить личное дело'").replace("[1]","[2]");

    public PersonaInfoPage(){
        //ждем окончания загрузки страницы
        $(By.xpath(editCaseButton)).waitUntil(Condition.enabled,7000);
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
        //ждем, когда поля станут доступными для редактирования
        String checkComboxEnabled = locators.getProperty("get_combobox_name_template");
        $$(By.xpath(checkComboxEnabled )).shouldHave(CollectionCondition.sizeGreaterThan(0));
        //ждем, когда поля станут доступными для редактирования
        String checkFieldsEnabled = locators.getProperty("get_input_field_name_template");
        $$(By.xpath(checkFieldsEnabled)).shouldHave(CollectionCondition.sizeGreaterThan(0));
        return this;
    }

    public PersonaInfoPage clickCancelCaseEditButton1() throws InterruptedException {
        $(By.xpath(cancelEditButton1)).shouldBe(Condition.enabled).click();
        //ждем, когда поле станет закрытым для редактирования
        $(By.xpath(countryResidence)).shouldHave(Condition.disabled);
        return this;
    }

    public PersonaInfoPage clickCancelCaseEditButton2() throws InterruptedException {
        $(By.xpath(cancelEditButton2)).shouldBe(Condition.enabled).click();
        //ждем, когда поле станет закрытым для редактирования
        $(By.xpath(position)).shouldHave(Condition.disabled);
        return this;
    }

}
