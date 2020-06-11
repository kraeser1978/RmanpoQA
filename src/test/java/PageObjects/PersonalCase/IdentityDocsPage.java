package PageObjects.PersonalCase;

import Common.RegressionTest;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import static PageObjects.PersonalCase.AddPassportPage.passportSeria;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class IdentityDocsPage extends RegressionTest {
    private static Logger logger = Logger.getLogger(IdentityDocsPage.class.getSimpleName());
    String addDocs = locators.getProperty("text_template").replace("''","'Добавить документ'");
    String residenceText = locators.getProperty("residenceText");
    public static String fileLoadedText = locators.getProperty("text_template").replace("''","'Загруженные файлы'");
    String passportAdded = locators.getProperty("passportAdded");

    public IdentityDocsPage(){
        $(By.xpath(addDocs)).shouldBe(Condition.visible);
    }

    public IdentityDocsPage isDocRemoved(){
        boolean flag = $(By.xpath(fileLoadedText)).isDisplayed();
        if (flag) logger.log(Level.SEVERE,"ОШИБКА: Паспортные данные не были удалены");
        else logger.log(Level.INFO,"Паспортные данные успешно удалены");
        return this;
    }

    public IdentityDocsPage clickRemoveDocButton(){
        $(By.xpath(locators.getProperty("remove_passport_button"))).shouldBe(Condition.enabled).click();
        $(By.xpath(locators.getProperty("remove_passport_confirmation_text"))).shouldBe(Condition.visible);
        String okButtonXpath = locators.getProperty("remove_passport_confirmation_button");
        $(By.xpath(okButtonXpath)).shouldBe(Condition.enabled).click();
        $(By.xpath(fileLoadedText)).shouldNotBe(Condition.visible);
        return this;
    }

    public IdentityDocsPage clickAddDocsButton(){
        $(By.xpath(addDocs)).shouldBe(Condition.enabled);
        $(By.xpath(addDocs + "//following-sibling::div")).shouldBe(Condition.enabled);
        $(By.xpath(addDocs)).click();
        return this;
    }

    public boolean isResidenceTextDisplayed(){ return $(By.xpath(residenceText)).isDisplayed(); }

    public AddPassportPage selectPassportRFOption(){
        $(By.xpath(locators.getProperty("select_passport_option"))).shouldBe(Condition.enabled).click();
        return page(AddPassportPage.class);
    }

    public IdentityDocsPage isPassportAdded(String passportFileName){
        //берем краткое имя прикрепленного файла
        int startPos = passportFileName.lastIndexOf("\\");
        String fileShortName = passportFileName.substring(startPos+1);
        //заменяем имя файла в строке шаблона на фактическое
        passportAdded = passportAdded.replace("passport_SKraevskiy_02.pdf",fileShortName);
        logger.log(Level.INFO,"проверяем отображения серии паспорта на вкладке Документы, удостоверяющие личность");
        boolean isPassportSeriaDisplayed = $(By.xpath(passportSeria)).isDisplayed();
        if (isPassportSeriaDisplayed) {
            String passportSeriaActualValue = $(By.xpath(passportSeria)).getAttribute("value");
            if (!passportSeriaActualValue.isEmpty()) logger.log(Level.INFO,"Паспортные данные были добавлены к личному делу студента");
        }
        else logger.log(Level.SEVERE,"ОШИБКА: Паспортные данные не добавлены");
        logger.log(Level.INFO,"проверка отображения краткого имени файла...");
        boolean isFileNameDisplayed = $(By.xpath(passportAdded)).isDisplayed();
        if (isFileNameDisplayed) logger.log(Level.INFO,"Скан паспорта был успешно добавлен");
        else logger.log(Level.SEVERE,"ОШИБКА: скан паспорта не добавлен");
        return this;
    }
}
