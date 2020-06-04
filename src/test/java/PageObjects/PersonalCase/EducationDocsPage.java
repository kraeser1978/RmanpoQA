package PageObjects.PersonalCase;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class EducationDocsPage {
    String addDocs = "(//span[text()='Добавить документ'])[2]";
    String residenceText = "(//span[contains(text(),'Заполните поле \"Гражданство\"')])[2]";

    public void clickAddDocsButton(){
        $(By.xpath(addDocs)).click();
    }

    public boolean isResidenceTextDisplayed(){ return $(By.xpath(residenceText)).isDisplayed(); }


}
