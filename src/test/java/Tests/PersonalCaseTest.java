package Tests;

import Common.RegressionTest;
import PageObjects.LoginPage;
import PageObjects.PersonalCase.IdentityDocsPage;
import PageObjects.PersonalCase.PersonaInfoPage;
import PageObjects.PersonalCase.StudentPersonalCasePage;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static PageObjects.PersonalCase.StudentPersonalCasePage.selectValueFromInputFile;
import static com.codeborne.selenide.Selenide.open;

public class PersonalCaseTest extends RegressionTest{
    private static Logger logger = Logger.getLogger(PersonalCaseTest.class.getSimpleName());

    @Test
    //метод тестирует добавление паспорта РФ в качестве документа в персональное дело слушателя и затем удаляет его
    public void test01_AddRemove_Passport() throws Exception {
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
    //метод тестирует заполнение всех полей вкладки Общая информация на экране Персональное дело слушателя
    //сначала каждое поле заполняется новым значением и проверяется, что значение сохранено
    //затем поле заполняется другим значением, жмется кнопка Отменить изменения и проверяется, что значение не было сохранено
    //в конце в поле записывается первоначальное значение (которое было до теста) и карточка сохраняется
    public void test02_Edit_All_Fields_GeneralInfoPage() throws Exception {
        StudentPersonalCasePage studentPersonalCasePage = new LoginPage().main();
        logger.log(Level.INFO,"тестируем поля вкладки Общая информация");

        //заполняем массивы списком заголовков полей для проверки редактирования
        studentPersonalCasePage.clickEditPersonalCaseButton();
        ArrayList<String> fieldTitles = studentPersonalCasePage.getFieldTitles();
        ArrayList<String> comboBoxTitles = studentPersonalCasePage.getComboBoxTitles();
        ArrayList<String> fieldTitlesDisabled = studentPersonalCasePage.getFieldTitlesDisabled();
        ArrayList<String> comboBoxTitlesDisabled = studentPersonalCasePage.getComboBoxTitlesDisabled();
        studentPersonalCasePage.clickCancelCaseEditButton();

        for (int i=0; i < fieldTitles.size();i++){
            //задаем заголовок поля для ввода
            String fieldName = fieldTitles.get(i);
            //считываем оригинальное значение
            String originalValue = studentPersonalCasePage.getFieldValue(fieldName);
            //задаем первое значение (которое будет сохранено взамен оригинального)
            String firstValue = selectValueFromInputFile(fieldName, 1);
            //задаем второе значение (которое будет подставлено взамен первого измененного, но сохранено не будет)
            String secondValue = selectValueFromInputFile(fieldName, 2);
            int p = i + 1;
            logger.log(Level.INFO, p + ". поле " + fieldName);
            studentPersonalCasePage
                    .clickEditPersonalCaseButton()
                    //задаем значение поля Общежитие
                    .setFieldValue(fieldName, firstValue)
                    //сохраняем
                    .clickSaveCaseButton()
                    //проверяем, что заданное значение сохранено
                    .isFieldValueAdded(fieldName, firstValue)
                    .clickEditPersonalCaseButton()
                    //вводим другое значение
                    .setFieldValue(fieldName, secondValue)
                    //жмем кнопку Отменить изменения
                    .clickCancelCaseEditButton()
                    //проверяем, что при отмене возвращается предыдущее заданное значение
                    .isFieldValueAdded(fieldName, firstValue)
                    //возвращаем оригинальное значение
                    .clickEditPersonalCaseButton()
                    .setFieldValue(fieldName, originalValue)
                    .clickSaveCaseButton();
        }

        for (int i=0; i < comboBoxTitles.size();i++){
            //задаем заголовок поля для ввода
            String fieldName = comboBoxTitles.get(i);
            //считываем оригинальное значение
            String originalValue = studentPersonalCasePage.getComboBoxValue(fieldName);
            //задаем первое значение (которое будет сохранено взамен оригинального)
            String firstValue = selectValueFromInputFile(fieldName, 1);
            //задаем второе значение (которое будет подставлено взамен первого измененного, но сохранено не будет)
            String secondValue = selectValueFromInputFile(fieldName, 2);
             int p = i + 1;
            logger.log(Level.INFO, i + ". поле " + fieldName);
            studentPersonalCasePage
                    .clickEditPersonalCaseButton()
                    //задаем значение поля Общежитие
                    .setComboBoxValue(fieldName, firstValue)
                    //сохраняем
                    .clickSaveCaseButton()
                    //проверяем, что заданное значение сохранено
                    .isComboBoxValueAdded(fieldName, firstValue)
                    .clickEditPersonalCaseButton()
                    //вводим другое значение
                    .setComboBoxValue(fieldName, secondValue)
                    //жмем кнопку Отменить изменения
                    .clickCancelCaseEditButton()
                    //проверяем, что при отмене возвращается предыдущее заданное значение
                    .isComboBoxValueAdded(fieldName, firstValue)
                    //возвращаем оригинальное значение
                    .clickEditPersonalCaseButton()
                    .setComboBoxValue(fieldName, originalValue)
                    .clickSaveCaseButton();
        }
    }

    @Test
    //метод тестирует заполнение некоторых полей вкладки Персональная информация на экране Персональное дело слушателя
    //сначала поле заполняется новым значением и проверяется, что значение сохранено
    //затем поле заполняется другим значением, жмется кнопка Отменить изменения и проверяется, что значение не было сохранено
    //в конце в поле записывается первоначальное значение (которое было до теста) и карточка сохраняется
    public void test03_Edit_Some_Fields_PersonalInfoPage() throws Exception {
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

    @Test
    //метод тестирует заполнение всех полей вкладки Персональная информация на экране Персональное дело слушателя
    //сначала каждое поле заполняется новым значением и проверяется, что значение сохранено
    //затем поле заполняется другим значением, жмется кнопка Отменить изменения и проверяется, что значение не было сохранено
    //в конце в поле записывается первоначальное значение (которое было до теста) и карточка сохраняется
    public void test04_Edit_All_Fields_PersonalInfoPage() throws Exception {
        StudentPersonalCasePage studentPersonalCasePage = new LoginPage().main();

        logger.log(Level.INFO,"тестируем поля вкладки Персональная информация");
        PersonaInfoPage personaInfoPage = studentPersonalCasePage.switchToPersonaInfoTab();

        //заполняем массивы списком заголовков полей для проверки редактирования
        personaInfoPage.clickEditPersonalCaseButton2();
        personaInfoPage.clickEditPersonalCaseButton();
        ArrayList<String> fieldTitles = personaInfoPage.getFieldTitles();
        ArrayList<String> comboBoxTitles = personaInfoPage.getComboBoxTitles();
        ArrayList<String> fieldTitlesDisabled = personaInfoPage.getFieldTitlesDisabled();
        ArrayList<String> comboBoxTitlesDisabled = personaInfoPage.getComboBoxTitlesDisabled();
        //извлекаем дубликаты имен, если они есть
        ArrayList<String> comboBoxTitlesDuplicates = getNameDuplicate(comboBoxTitles);
        ArrayList<String> fieldTitlesDuplicates = getNameDuplicate(fieldTitles);
        //очищаем от дубликатов
        comboBoxTitles = getUniqueName(comboBoxTitles);
        fieldTitles = getUniqueName(fieldTitles);

        personaInfoPage.clickCancelCaseEditButton();

        for (int i=0; i < fieldTitles.size();i++){
            //задаем заголовок поля для ввода
            String fieldName = fieldTitles.get(i);
            //считываем оригинальное значение
            String originalValue = personaInfoPage.getFieldValue(fieldName);
            //задаем первое значение (которое будет сохранено взамен оригинального)
            String firstValue = selectValueFromInputFile(fieldName, 1);
            //задаем второе значение (которое будет подставлено взамен первого измененного, но сохранено не будет)
            String secondValue = selectValueFromInputFile(fieldName, 2);
            int p = i + 1;
            logger.log(Level.INFO, p + ". поле " + fieldName);
            personaInfoPage
                    .clickEditPersonalCaseButton()
                    //задаем значение поля Общежитие
                    .setFieldValue(fieldName, firstValue);
                    //сохраняем
                    personaInfoPage.clickSaveCaseButton()
                    //проверяем, что заданное значение сохранено
                    .isFieldValueAdded(fieldName, firstValue)
                    .clickEditPersonalCaseButton()
                    //вводим другое значение
                    .setFieldValue(fieldName, secondValue)
                    //жмем кнопку Отменить изменения
                    .clickCancelCaseEditButton()
                    //проверяем, что при отмене возвращается предыдущее заданное значение
                    .isFieldValueAdded(fieldName, firstValue)
                    //возвращаем оригинальное значение
                    .clickEditPersonalCaseButton()
                    .setFieldValue(fieldName, originalValue)
                    .clickSaveCaseButton();
        }

        for (int i=0; i < comboBoxTitles.size();i++){
            //задаем заголовок поля для ввода
            String fieldName = comboBoxTitles.get(i);
            //считываем оригинальное значение
            String originalValue = studentPersonalCasePage.getComboBoxValue(fieldName);
            //задаем первое значение (которое будет сохранено взамен оригинального)
            String firstValue = selectValueFromInputFile(fieldName, 1);
            //задаем второе значение (которое будет подставлено взамен первого измененного, но сохранено не будет)
            String secondValue = selectValueFromInputFile(fieldName, 2);
            int p = i + 1;
            logger.log(Level.INFO, i + ". поле " + fieldName);
            studentPersonalCasePage
                    .clickEditPersonalCaseButton()
                    //задаем значение поля Общежитие
                    .setComboBoxValue(fieldName, firstValue)
                    //сохраняем
                    .clickSaveCaseButton()
                    //проверяем, что заданное значение сохранено
                    .isComboBoxValueAdded(fieldName, firstValue)
                    .clickEditPersonalCaseButton()
                    //вводим другое значение
                    .setComboBoxValue(fieldName, secondValue)
                    //жмем кнопку Отменить изменения
                    .clickCancelCaseEditButton()
                    //проверяем, что при отмене возвращается предыдущее заданное значение
                    .isComboBoxValueAdded(fieldName, firstValue)
                    //возвращаем оригинальное значение
                    .clickEditPersonalCaseButton()
                    .setComboBoxValue(fieldName, originalValue)
                    .clickSaveCaseButton();
        }
    }

}
