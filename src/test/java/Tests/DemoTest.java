package Tests;

import Common.Props;
import Common.RegressionTest;
import PageObjects.LoginPage;
import PageObjects.PersonalCase.IdentityDocsPage;
import PageObjects.RmanpoDpoMainPage;
import PageObjects.RmanpoMainPage;
import PageObjects.PersonalCase.StudentPersonalCasePage;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.hasWebDriverStarted;

public class DemoTest extends RegressionTest{

    @Test
    public void test01_AddRemoveIdentityDocs() throws Exception {
        String propsFilePath = System.getenv("Rmanpo_autotest_settings");
        String paramsFile = FileUtils.readFileToString(new File(propsFilePath), "UTF-8");
        Props props = new Props(paramsFile);
        StudentPersonalCasePage studentPersonalCasePage = new LoginPage().main();
        //переходим на вкладку Документы, удостоверяющие личность
        IdentityDocsPage identityDocsPage = studentPersonalCasePage.switchToIdentityDocsTab();
        //проверяем, отображается ли текст Заполните поле "Гражданство"
        boolean checkResidenceText = identityDocsPage.isResidenceTextDisplayed();
        //если да, переходим на вкладку Персональные данные и указываем гражданство слушателя
        if (checkResidenceText) {
            studentPersonalCasePage.switchToPersonaInfoTab()
                    .setCountryResidence("РОССИЙСКАЯ ФЕДЕРАЦИЯ")
                    //возвращаемся обратно на вкладку Документов
                    .switchToIdentityDocsTab();
        }
        //жмем кнопку Добавить документ, выбираем Паспорт РФ
        identityDocsPage.clickAddDocsButton().selectPassportRFOption()
                //вводим паспортные данные
                .setPassportSeria()
                .setPassportNumber()
                .setPassportDateOfIssue()
                .setPassportDivisionCode()
                .setPassportIssuer()
                .setPermanentRegistration()
                //загружаем скан паспорта
                .selectUploadFile(props.passportScanFile())
                //сохраняем
                .clickSaveButton()
                //проверяем, что документ и паспортные данные добавлены на вкладке Документы, удостоверяющие личность
                .isPassportAdded(props.passportScanFile())
                //теперь удаляем прикрепленный документ
                .clickRemoveDocButton()
                //проверяем, что документ удален
                .isDocRemoved();
        tearDown();
    }

    @Test
    public void test02_PersonalCaseGeneralInfoPage() throws Exception {
        String propsFilePath = System.getenv("Rmanpo_autotest_settings");
        String paramsFile = FileUtils.readFileToString(new File(propsFilePath), "UTF-8");
        Props props = new Props(paramsFile);
        StudentPersonalCasePage studentPersonalCasePage = new LoginPage().main();
        //редактируем поля вкладки Общая информация
        String originalCampus = studentPersonalCasePage.getCampus();
        if (originalCampus.contains("Без общежития")) {
            studentPersonalCasePage
                    .clickEditPersonalCaseButton()
                    //задаем значение поля Общежитие
                    .setCampus("Общежитие на ул. Смольная")
                    //сохраняем
                    .clickSaveCaseButton()
                    //проверяем, что заданное значение сохранено
                    .isCampusAdded("Общежитие на ул. Смольная")
                    .clickEditPersonalCaseButton()
                    //вводим другое значение
                    .setCampus("Общежитие на Ленинградском шоссе")
                    //жмем кнопку Отменить изменения
                    .clickCancelCaseEditButton()
                    //проверяем, что при отмене возвращается предыдущее заданное значение
                    .isCampusAdded("Общежитие на ул. Смольная")
                    //возвращаем оригинальное значение
                    .clickEditPersonalCaseButton()
                    .setCampus(originalCampus)
                    .clickSaveCaseButton();
        }
    }

    @Test
    public void test03_PersonalCasePersonalInfoPage() throws Exception {
        String propsFilePath = System.getenv("Rmanpo_autotest_settings");
        String paramsFile = FileUtils.readFileToString(new File(propsFilePath), "UTF-8");
        Props props = new Props(paramsFile);
        StudentPersonalCasePage studentPersonalCasePage = new LoginPage().main();
        //редактируем поля вкладки Общая информация
        String originalFamilyName = studentPersonalCasePage.getCampus();
        if (originalFamilyName.contains("Без общежития")) {
            studentPersonalCasePage
                    .clickEditPersonalCaseButton()
                    //задаем значение поля Общежитие
                    .setCampus("Общежитие на ул. Смольная")
                    //сохраняем
                    .clickSaveCaseButton()
                    //проверяем, что заданное значение сохранено
                    .isCampusAdded("Общежитие на ул. Смольная")
                    .clickEditPersonalCaseButton()
                    //вводим другое значение
                    .setCampus("Общежитие на Ленинградском шоссе")
                    //жмем кнопку Отменить изменения
                    .clickCancelCaseEditButton()
                    //проверяем, что при отмене возвращается предыдущее заданное значение
                    .isCampusAdded("Общежитие на ул. Смольная")
                    //возвращаем оригинальное значение
                    .clickEditPersonalCaseButton()
                    .setCampus(originalFamilyName)
                    .clickSaveCaseButton();
        }
    }
}
