package PageObjects.PersonalCase;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.$;

public class PersonaInfoPage extends StudentPersonalCasePage {
    String countryResidence = "//span[text()='Гражданство']//parent::div/following-sibling::div/span/input[contains(@class,'combobox-input')]";

    public PersonaInfoPage(){
        //ждем окончания загрузки страницы
        $(By.xpath(editCase)).waitUntil(Condition.enabled,7000);
    }

    public PersonaInfoPage setCountryResidence(String countryName){
        clickEditPersonalCaseButton();
        $(By.xpath(countryResidence)).setValue(countryName);
        return this;
    }
}
