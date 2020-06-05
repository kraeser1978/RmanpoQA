package PageObjects.PersonalCase;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import java.io.File;
import static PageObjects.PersonalCase.AddPassportPage.passportSeria;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class IdentityDocsPage {
    String addDocs = "(//span[text()='Добавить документ'])[1]";
    String residenceText = "(//span[contains(text(),'Заполните поле \"Гражданство\"')])[1]";
    public static String fileLoadedText = "//span[text()='Загруженные файлы']";
    String passportAdded = "//input[contains(@class,'textbox-disabled') and @value='passport_SKraevskiy_02.pdf']";

    public IdentityDocsPage(){
        $(By.xpath(addDocs)).shouldBe(Condition.visible);
    }

    public IdentityDocsPage isDocRemoved(){
        boolean flag = $(By.xpath(fileLoadedText)).isDisplayed();
        if (flag) System.out.println("ОШИБКА: Паспортные данные не были удалены");
        else System.out.println("Паспортные данные успешно удалены");
        return this;
    }

    public IdentityDocsPage clickRemoveDocButton(){
        $(By.xpath("//a[@title='Удалить документ']")).shouldBe(Condition.enabled).click();
        $(By.xpath("//span[text()='Удалить документ и все его файлы?']")).shouldBe(Condition.visible);
        $(By.xpath("//button[text()='ОК']")).shouldBe(Condition.enabled).click();
        $(By.xpath(fileLoadedText)).shouldNotBe(Condition.visible);
        return this;
    }

    public IdentityDocsPage clickAddDocsButton(){
        $(By.xpath(addDocs)).shouldBe(Condition.enabled);
        $(By.xpath(addDocs+"//following-sibling::div")).shouldBe(Condition.enabled);
        $(By.xpath(addDocs)).click();
        return this;
    }

    public boolean isResidenceTextDisplayed(){ return $(By.xpath(residenceText)).isDisplayed(); }

    public AddPassportPage selectPassportRFOption(){
        $(By.xpath("//span[contains(@class,'menuitem-text') and text()='Паспорт гражданина РФ']")).shouldBe(Condition.enabled).click();
        return page(AddPassportPage.class);
    }

    public IdentityDocsPage isPassportAdded(String passportFileName){
        //берем краткое имя прикрепленного файла
        int startPos = passportFileName.lastIndexOf("\\");
        String fileShortName = passportFileName.substring(startPos+1);
        //заменяем имя файла в стркое шаблона на фактическое
        passportAdded = passportAdded.replace("passport_SKraevskiy_02.pdf",fileShortName);
        //проверяем отображения серии паспорта на вкладке Документы, удостоверяющие личность
        boolean isPassportSeriaDisplayed = $(By.xpath(passportSeria)).isDisplayed();
        if (isPassportSeriaDisplayed) {
            String passportSeriaActualValue = $(By.xpath(passportSeria)).getAttribute("value");
            if (!passportSeriaActualValue.isEmpty()) System.out.println("Паспортные данные были добавлены к личному делу студента");
        }
        else System.out.println("ОШИБКА: Паспортные данные не добавлены");
        //проверка отображения краткого имени файла
        boolean isFileNameDisplayed = $(By.xpath(passportAdded)).isDisplayed();
        if (isFileNameDisplayed) System.out.println("Скан паспорта был успешно добавлен");
        else System.out.println("ОШИБКА: скан паспорта не добавлен");
        return this;
    }
}
