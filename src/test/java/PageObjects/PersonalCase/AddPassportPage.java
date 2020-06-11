package PageObjects.PersonalCase;

import Common.RegressionTest;
import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import static PageObjects.PersonalCase.IdentityDocsPage.fileLoadedText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class AddPassportPage extends RegressionTest {
    private static Logger logger = Logger.getLogger(AddPassportPage.class.getSimpleName());
    public static String passportSeria = locators.getProperty("passport_input_field_template").replace("''","'Серия'");
    String passportNumber = locators.getProperty("passport_input_field_template").replace("''","'Номер'");
    String passportDateOfIssue = locators.getProperty("passport_date_field_template").replace("''","'Дата выдачи'");
    String passportDivisionCode = locators.getProperty("passport_input_field_template").replace("''","'Код подразделения'");
    String passportIssuer = locators.getProperty("passport_input_field_template").replace("''","'Кем выдан'");
    String permanentRegistration = locators.getProperty("passport_input_field_template").replace("''","'Место постоянной регистрации'");
    String fileUploadButton = locators.getProperty("fileUploadButton");
    String uploadPath = locators.getProperty("uploadPath");
    String saveButton = locators.getProperty("saveButton");

    public AddPassportPage(){
        String warningText = locators.getProperty("text_template")
                .replace("''","'Необходимо прикрепить отсканированный паспорт, содержащий основную страницу и страницу с пропиской'");
        $(By.xpath(warningText)).shouldBe(Condition.visible);
    }

    public AddPassportPage setPassportSeria(){
        $(By.xpath(passportSeria)).shouldBe(Condition.enabled).setValue(dataInput.get("Серия"));
        return this;
    }

    public AddPassportPage setPassportNumber(){
        $(By.xpath(passportNumber)).shouldBe(Condition.enabled).setValue(dataInput.get("Номер"));
        return this;
    }

    public AddPassportPage setPassportDateOfIssue(){
        $(By.xpath(passportDateOfIssue)).shouldBe(Condition.enabled).setValue(dataInput.get("Дата выдачи"));
        return this;
    }

    public AddPassportPage setPassportDivisionCode(){
        $(By.xpath(passportDivisionCode)).shouldBe(Condition.enabled).setValue(dataInput.get("Код подразделения"));
        return this;
    }

    public AddPassportPage setPassportIssuer(){
        $(By.xpath(passportIssuer)).shouldBe(Condition.enabled).setValue(dataInput.get("Кем выдан"));
        return this;
    }

    public AddPassportPage setPermanentRegistration(){
        $(By.xpath(permanentRegistration)).shouldBe(Condition.enabled).setValue(dataInput.get("Место постоянной регистрации"));
        return this;
    }

    public AddPassportPage clickFileUploadButton(){
        $(By.xpath(fileUploadButton)).shouldBe(Condition.enabled).click();
        return this;
    }

    public AddPassportPage selectUploadFile(String fullFileName) throws InterruptedException {
        File file = new File(fullFileName);
        $(By.xpath(uploadPath)).uploadFile(file);
        logger.log(Level.INFO,"ждем окончания загрузки файла...");
        $(By.xpath(locators.getProperty("waitFileUploadMessage"))).waitWhile(Condition.visible,7000);
        Thread.sleep(1000);
        return this;
    }

    public IdentityDocsPage clickSaveButton(){
        $(By.xpath(saveButton)).shouldBe(Condition.enabled).click();
        $(By.xpath(fileLoadedText)).shouldBe(Condition.visible);
        return page(IdentityDocsPage.class);
    }
}
