package Tests;

import Common.Props;
import Common.RegressionTest;
import PageObjects.LoginPage;
import PageObjects.PersonalCase.IdentityDocsPage;
import PageObjects.PersonalCase.PersonaInfoPage;
import PageObjects.RmanpoDpoMainPage;
import PageObjects.RmanpoMainPage;
import PageObjects.PersonalCase.StudentPersonalCasePage;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.hasWebDriverStarted;

public class DemoTest extends RegressionTest{
    private static Logger logger = Logger.getLogger(DemoTest.class.getSimpleName());

    @Test
    public void test01_AddRemoveIdentityDocs() throws Exception {
        String propsFilePath = System.getenv("Rmanpo_autotest_settings");
        String paramsFile = FileUtils.readFileToString(new File(propsFilePath), "UTF-8");
        Props props = new Props(paramsFile);
        StudentPersonalCasePage studentPersonalCasePage = new LoginPage().main();
        logger.log(Level.INFO,"переходим на вкладку Документы, удостоверяющие личность");
        IdentityDocsPage identityDocsPage = studentPersonalCasePage.switchToIdentityDocsTab();
        logger.log(Level.INFO,"проверяем, отображается ли текст Заполните поле \"Гражданство\"");
        boolean checkResidenceText = identityDocsPage.isResidenceTextDisplayed();
        if (checkResidenceText) {
            logger.log(Level.INFO,"отображается - тогда переходим на вкладку Персональные данные и указываем гражданство слушателя");
            studentPersonalCasePage.switchToPersonaInfoTab()
                    .setCountryResidence("РОССИЙСКАЯ ФЕДЕРАЦИЯ")
                    //возвращаемся обратно на вкладку Документов
                    .switchToIdentityDocsTab();
        }
        logger.log(Level.INFO,"жмем кнопку Добавить документ, выбираем Паспорт РФ");
        logger.log(Level.INFO,"вводим паспортные данные");
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
    }

    @Test
    public void test02_PersonalCaseGeneralInfoPage() throws Exception {
        String propsFilePath = System.getenv("Rmanpo_autotest_settings");
        String paramsFile = FileUtils.readFileToString(new File(propsFilePath), "UTF-8");
        Props props = new Props(paramsFile);
        StudentPersonalCasePage studentPersonalCasePage = new LoginPage().main();

        logger.log(Level.INFO,"тестируем поля вкладки Общая информация");
        String originalCampus = studentPersonalCasePage.getCampus();

        logger.log(Level.INFO,"1. поле Общежитие");
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

        logger.log(Level.INFO,"тестируем поля вкладки Персональная информация");
        PersonaInfoPage personaInfoPage = studentPersonalCasePage.switchToPersonaInfoTab();

        logger.log(Level.INFO,"1. поле Фамилия");
        String originalFamilyValue = personaInfoPage.getFieldValue("Фамилия");
        personaInfoPage
                    .clickEditPersonalCaseButton()
                    //задаем значение поля Фамилия
                    .setFieldValue("Фамилия","Пивораки")
                    //сохраняем
                    .clickSaveCaseButton()
                    //проверяем, что заданное значение сохранено
                    .isFieldValueAdded("Фамилия","Пивораки")
                    .clickEditPersonalCaseButton()
                    //вводим другое значение
                    .setFieldValue("Фамилия","Пупкин");
                    //жмем кнопку Отменить изменения
                    personaInfoPage.clickCancelCaseEditButton1()
                    //проверяем, что при отмене возвращается предыдущее заданное значение
                    .isFieldValueAdded("Фамилия","Пивораки")
                    //возвращаем оригинальное значение
                    .clickEditPersonalCaseButton()
                    .setFieldValue("Фамилия",originalFamilyValue)
                    .clickSaveCaseButton();

        logger.log(Level.INFO,"2. поле Город");
        String originalCityValue = personaInfoPage.getFieldValue("Город");
        personaInfoPage
                .clickEditPersonalCaseButton2()
                //задаем значение поля
                .setFieldValue("Город","Москва");
                //сохраняем
                personaInfoPage.clickSaveCaseButton2()
                //проверяем, что заданное значение сохранено
                .isFieldValueAdded("Город","Москва");
                personaInfoPage.clickEditPersonalCaseButton2()
                //вводим другое значение
                .setFieldValue("Город","Суздаль");
                //жмем кнопку Отменить изменения
                personaInfoPage.clickCancelCaseEditButton2()
                //проверяем, что при отмене возвращается предыдущее заданное значение
                .isFieldValueAdded("Город","Москва");
                //возвращаем оригинальное значение
                personaInfoPage.clickEditPersonalCaseButton2()
                .setFieldValue("Город",originalCityValue);
                personaInfoPage.clickSaveCaseButton2();

        logger.log(Level.INFO,"3. поле Должность");
        String originalPositionValue = personaInfoPage.getFieldValue("Должность");
        personaInfoPage
                .clickEditPersonalCaseButton2()
                //задаем значение поля
                .setFieldValue("Должность","Главный специалист");
                //сохраняем
                personaInfoPage.clickSaveCaseButton2()
                //проверяем, что заданное значение сохранено
                .isFieldValueAdded("Должность","Главный специалист");
                personaInfoPage.clickEditPersonalCaseButton2()
                //вводим другое значение
                .setFieldValue("Должность","стажер");
                //жмем кнопку Отменить изменения
                personaInfoPage.clickCancelCaseEditButton2()
                //проверяем, что при отмене возвращается предыдущее заданное значение
                .isFieldValueAdded("Должность","Главный специалист");
                //возвращаем оригинальное значение
                personaInfoPage.clickEditPersonalCaseButton2()
                .setFieldValue("Должность",originalPositionValue);
                personaInfoPage.clickSaveCaseButton2();
    }

}
