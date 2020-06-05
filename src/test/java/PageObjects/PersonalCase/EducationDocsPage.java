package PageObjects.PersonalCase;

import org.openqa.selenium.By;

import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.$;

public class EducationDocsPage {
    private static Logger logger = Logger.getLogger(EducationDocsPage.class.getSimpleName());
    String addDocs = "(//span[text()='Добавить документ'])[2]";
    String residenceText = "(//span[contains(text(),'Заполните поле \"Гражданство\"')])[2]";

    public void clickAddDocsButton(){
        $(By.xpath(addDocs)).click();
    }

    public boolean isResidenceTextDisplayed(){ return $(By.xpath(residenceText)).isDisplayed(); }


}
