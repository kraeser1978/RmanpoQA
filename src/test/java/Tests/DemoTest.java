package Tests;

import Common.RegressionTest;
import PageObjects.LoginPage;
import PageObjects.PersonalCase.IdentityDocsPage;
import PageObjects.PersonalCase.PersonaInfoPage;
import PageObjects.PersonalCase.StudentPersonalCasePage;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static PageObjects.PersonalCase.StudentPersonalCasePage.selectValueFromInputFile;
import static com.codeborne.selenide.Selenide.open;

public class DemoTest extends RegressionTest{
    private static Logger logger = Logger.getLogger(DemoTest.class.getSimpleName());

    @Test
    public void test01_AddRemoveIdentityDocs() throws Exception {
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
        StudentPersonalCasePage studentPersonalCasePage = new LoginPage().main();

        logger.log(Level.INFO,"тестируем поля вкладки Общая информация");
        String originalCampus = studentPersonalCasePage.getComboBoxValue("Общежитие");
        String firstValue = selectValueFromInputFile("Общежитие", 1);
        String secondValue = selectValueFromInputFile("Общежитие", 2);

        logger.log(Level.INFO,"1. поле Общежитие");
        studentPersonalCasePage
                .clickEditPersonalCaseButton()
                //задаем значение поля Общежитие
                .setComboBoxValue("Общежитие", firstValue)
                //сохраняем
                .clickSaveCaseButton()
                //проверяем, что заданное значение сохранено
                .isComboBoxValueAdded("Общежитие", firstValue)
                .clickEditPersonalCaseButton()
                //вводим другое значение
                .setComboBoxValue("Общежитие", secondValue)
                //жмем кнопку Отменить изменения
                .clickCancelCaseEditButton()
                //проверяем, что при отмене возвращается предыдущее заданное значение
                .isComboBoxValueAdded("Общежитие", firstValue)
                //возвращаем оригинальное значение
                .clickEditPersonalCaseButton()
                .setComboBoxValue("Общежитие", originalCampus)
                .clickSaveCaseButton();
    }

    @Test
    public void test03_PersonalCasePersonalInfoPage() throws Exception {
        StudentPersonalCasePage studentPersonalCasePage = new LoginPage().main();

        logger.log(Level.INFO,"тестируем поля вкладки Персональная информация");
        PersonaInfoPage personaInfoPage = studentPersonalCasePage.switchToPersonaInfoTab();

        logger.log(Level.INFO,"1. поле Фамилия");
        String originalFamilyValue = personaInfoPage.getFieldValue("Фамилия");
        String firstValue = selectValueFromInputFile("Фамилия", 1);
        String secondValue = selectValueFromInputFile("Фамилия", 2);

        personaInfoPage
                    .clickEditPersonalCaseButton()
                    //задаем значение поля Фамилия
                    .setFieldValue("Фамилия",firstValue)
                    //сохраняем
                    .clickSaveCaseButton()
                    //проверяем, что заданное значение сохранено
                    .isFieldValueAdded("Фамилия",firstValue)
                    .clickEditPersonalCaseButton()
                    //вводим другое значение
                    .setFieldValue("Фамилия",secondValue);
                    //жмем кнопку Отменить изменения
                    personaInfoPage.clickCancelCaseEditButton1()
                    //проверяем, что при отмене возвращается предыдущее заданное значение
                    .isFieldValueAdded("Фамилия",firstValue)
                    //возвращаем оригинальное значение
                    .clickEditPersonalCaseButton()
                    .setFieldValue("Фамилия",originalFamilyValue)
                    .clickSaveCaseButton();

        logger.log(Level.INFO,"2. поле Город");
        String originalCityValue = personaInfoPage.getFieldValue("Город");
        firstValue = selectValueFromInputFile("Город", 1);
        secondValue = selectValueFromInputFile("Город", 2);

        personaInfoPage
                .clickEditPersonalCaseButton2()
                //задаем значение поля
                .setFieldValue("Город",firstValue);
                //сохраняем
                personaInfoPage.clickSaveCaseButton2()
                //проверяем, что заданное значение сохранено
                .isFieldValueAdded("Город",firstValue);
                personaInfoPage.clickEditPersonalCaseButton2()
                //вводим другое значение
                .setFieldValue("Город",secondValue);
                //жмем кнопку Отменить изменения
                personaInfoPage.clickCancelCaseEditButton2()
                //проверяем, что при отмене возвращается предыдущее заданное значение
                .isFieldValueAdded("Город",firstValue);
                //возвращаем оригинальное значение
                personaInfoPage.clickEditPersonalCaseButton2()
                .setFieldValue("Город",originalCityValue);
                personaInfoPage.clickSaveCaseButton2();

        logger.log(Level.INFO,"3. поле Должность");
        String originalPositionValue = personaInfoPage.getFieldValue("Должность");
        firstValue = selectValueFromInputFile("Должность", 1);
        secondValue = selectValueFromInputFile("Должность", 2);

        personaInfoPage
                .clickEditPersonalCaseButton2()
                //задаем значение поля
                .setFieldValue("Должность",firstValue);
                //сохраняем
                personaInfoPage.clickSaveCaseButton2()
                //проверяем, что заданное значение сохранено
                .isFieldValueAdded("Должность",firstValue);
                personaInfoPage.clickEditPersonalCaseButton2()
                //вводим другое значение
                .setFieldValue("Должность",secondValue);
                //жмем кнопку Отменить изменения
                personaInfoPage.clickCancelCaseEditButton2()
                //проверяем, что при отмене возвращается предыдущее заданное значение
                .isFieldValueAdded("Должность",firstValue);
                //возвращаем оригинальное значение
                personaInfoPage.clickEditPersonalCaseButton2()
                .setFieldValue("Должность",originalPositionValue);
                personaInfoPage.clickSaveCaseButton2();
    }

}
