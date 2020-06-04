package PageObjects.PersonalCase;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import java.io.File;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class IdentityDocsPage {
    String addDocs = "(//span[text()='Добавить документ'])[1]";
    String residenceText = "(//span[contains(text(),'Заполните поле \"Гражданство\"')])[1]";
    String passportRFOption = "//span[text()='Паспорт гражданина РФ']";
    String fileLoadedText = "//span[text()='Загруженные файлы']";
    String passportAdded = "//input[contains(@class,'textbox-disabled') and @value='passport_SKraevskiy_02.pdf']";

    public IdentityDocsPage(){
        $(By.xpath(addDocs)).shouldBe(Condition.visible);
    }

    public IdentityDocsPage clickAddDocsButton(){
        $(By.xpath(addDocs)).click();
        return this;
    }

    public boolean isResidenceTextDisplayed(){ return $(By.xpath(residenceText)).isDisplayed(); }

    public AddPassportPage selectPassportRFOption(){
        $(By.xpath("//span[contains(@class,'menuitem-text') and text()='Паспорт гражданина РФ']")).shouldBe(Condition.enabled).click();
        return page(AddPassportPage.class);
    }

    public boolean isPassportAdded(String passportFileName){
        boolean flag = false;
        //берем краткое имя прикрепленного файла
        int startPos = passportFileName.lastIndexOf("\\");
        String fileShortName = passportFileName.substring(startPos);
        //заменяем имя файла в стркое шаблона на фактическое
        passportAdded = passportAdded.replace("passport_SKraevskiy_02.pdf",fileShortName);
        //проверяем, что текст Загруженные файлы и краткое имя файла отображаются на вкладке Документы, удостоверяющие личность
        if ($(By.xpath(fileLoadedText)).isDisplayed() && ($(By.xpath(passportAdded)).isDisplayed())) flag = true;
        return flag;
    }
}
