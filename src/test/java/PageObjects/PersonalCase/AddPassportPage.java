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
    public static String passportSeria = "//span[text()='Серия']//parent::td/following-sibling::td/input[contains(@class,'textbox')]";
    String passportNumber = "//span[text()='Номер']//parent::td/following-sibling::td/input[contains(@class,'textbox')]";
    String passportDateOfIssue = "//span[text()='Дата выдачи']//parent::td/following-sibling::td/span/input[contains(@class,'datebox')]";
    String passportDivisionCode = "//span[text()='Код подразделения']//parent::td/following-sibling::td/input[contains(@class,'textbox')]";
    String passportIssuer = "//span[text()='Кем выдан']//parent::td/following-sibling::td/input[contains(@class,'textbox')]";
    String permanentRegistration = "//span[text()='Место постоянной регистрации']//parent::td/following-sibling::td/input[contains(@class,'textbox')]";
    String fileUploadButton = "//a[@title='Загрузить файлы']";
    String uploadPath = "//input[@name='file' and @type='file']";
    String saveButton = "//button[text()='Сохранить']";

    public AddPassportPage(){
        $(By.xpath("//span[text()='Необходимо прикрепить отсканированный паспорт, содержащий основную страницу и страницу с пропиской']"))
                .shouldBe(Condition.visible);
    }

    public AddPassportPage setPassportSeria(){
        $(By.xpath(passportSeria)).shouldBe(Condition.enabled).setValue("4604");
        return this;
    }

    public AddPassportPage setPassportNumber(){
        $(By.xpath(passportNumber)).shouldBe(Condition.enabled).setValue("958995");
        return this;
    }

    public AddPassportPage setPassportDateOfIssue(){
        $(By.xpath(passportDateOfIssue)).shouldBe(Condition.enabled).setValue("15.07.2003");
        return this;
    }

    public AddPassportPage setPassportDivisionCode(){
        $(By.xpath(passportDivisionCode)).shouldBe(Condition.enabled).setValue("503-034");
        return this;
    }

    public AddPassportPage setPassportIssuer(){
        $(By.xpath(passportIssuer)).shouldBe(Condition.enabled).setValue("Коломенским УВД Московской обл.");
        return this;
    }

    public AddPassportPage setPermanentRegistration(){
        $(By.xpath(permanentRegistration)).shouldBe(Condition.enabled).setValue("127224, г. Москва, пр-д Шокальского, д.63, кв.32");
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
        $(By.xpath("//span[contains(text(),'Размер файла')]")).waitWhile(Condition.visible,7000);
        Thread.sleep(1000);
        return this;
    }

    public IdentityDocsPage clickSaveButton(){
        $(By.xpath(saveButton)).shouldBe(Condition.enabled).click();
        $(By.xpath(fileLoadedText)).shouldBe(Condition.visible);
        return page(IdentityDocsPage.class);
    }
}
