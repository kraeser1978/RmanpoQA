package PageObjects.PersonalCase;

import Common.RegressionTest;
import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.*;

public class StudentPersonalCasePage extends RegressionTest {
    private static Logger logger = Logger.getLogger(StudentPersonalCasePage.class.getSimpleName());
    String editCase = "(//button[@title='Изменить личное дело'])[1]";
    String cancelCaseEdit = "//button[@title='Отменить изменения']";
    String saveCase = "(//button[@title='Сохранить изменения'])[1]";

    public StudentPersonalCasePage(){
        switchTo().window(1);
        //ждем окончания загрузки страницы
        $(By.xpath(editCase)).waitUntil(Condition.enabled,7000);
    }

    public static String selectValueFromInputFile(String fieldName, int propertySeqNo){
        String result = "";
        String allFieldValues = dataInput.get(fieldName);
        if (propertySeqNo == 1) result = allFieldValues.substring(0,allFieldValues.indexOf(";"));
        else result = allFieldValues.substring(allFieldValues.indexOf(";") + 1);
        return result;
    }

    public String getFieldValue(String fieldName){
        String fieldValueXpath = locators.getProperty("set_input_field_value_template")
                .replace("''","'" + fieldName + "'");
        String fieldValue = $(By.xpath(fieldValueXpath)).getAttribute("value");
        return fieldValue;
    }

    public String getComboBoxValue(String fieldName){
        String comboBoxValueXpath = locators.getProperty("set_combobox_value_template")
                .replace("''","'" + fieldName + "'");
        String comboBoxValue = $(By.xpath(comboBoxValueXpath)).getAttribute("value");
        return comboBoxValue;
    }

    public StudentPersonalCasePage setFieldValue(String fieldName, String fieldValue){
        String fieldValueXpath = locators.getProperty("set_input_field_value_template")
                .replace("''","'" + fieldName + "'");
        $(By.xpath(fieldValueXpath)).shouldBe(Condition.enabled).setValue(fieldValue);
        logger.log(Level.INFO,"задаем поле " + fieldName + " = " + fieldValue);
        return this;
    }

    public StudentPersonalCasePage setComboBoxValue(String fieldName, String fieldValue){
        String comboBoxValueXpath = locators.getProperty("set_combobox_value_template")
                .replace("''","'" + fieldName + "'");
        //определяем тип комбобокса - с зафиксированными значениями или нет
        boolean isComboReadOnly = $(By.xpath(comboBoxValueXpath)).has(Condition.attribute("readonly"));
        if (isComboReadOnly) {
            //жмем стрелку для раскрытия списка значений
            String comboBoxDropDownXpath = locators.getProperty("select_dropdown_list_template")
                    .replace("''","'" + fieldName + "'");
            $(By.xpath(comboBoxDropDownXpath)).shouldBe(Condition.enabled).click();
            //выбираем значение
            String comboBoxDropDownValueXpath = locators.getProperty("select_combobox_value_from_dropdown_template")
                    .replace("''","'" + fieldValue + "'");
            //обработка ситуации со спецсимволами вместо пробелов в тексте элемента
            int specialCharPosition = fieldValue.indexOf(" ");
            if (specialCharPosition != -1){
                String firstPartOfValue = fieldValue.substring(0,specialCharPosition);
                comboBoxDropDownValueXpath = locators.getProperty("select_combobox_partial_value_from_dropdown_template")
                        .replace("''","'" + firstPartOfValue + "'");
            }
            $(By.xpath(comboBoxDropDownValueXpath)).shouldBe(Condition.visible).click();
        }
        else $(By.xpath(comboBoxValueXpath)).shouldBe(Condition.enabled).setValue(fieldValue);
        logger.log(Level.INFO,"задаем поле " + fieldName + " = " + fieldValue);
        return this;
    }

    private void compareFieldValues(String fieldName, String actualValue, String expectedValue){
        if (actualValue.contains(expectedValue)) logger.log(Level.INFO,"Значение поля " + fieldName + " совпало с ранее введенным");
        else {
            logger.log(Level.SEVERE,"ОШИБКА: значение поля " + fieldName + " отличается от ожидаемого:");
            logger.log(Level.INFO,"Ожидаемое = " + expectedValue + " , фактическое = " + actualValue);
        }
    }

    public StudentPersonalCasePage isFieldValueAdded(String fieldName, String expectedValue){
        String actualValue = getFieldValue(fieldName);
        compareFieldValues(fieldName, actualValue, expectedValue);
        return this;
    }

    public StudentPersonalCasePage isComboBoxValueAdded(String fieldName, String expectedValue){
        String actualValue = getComboBoxValue(fieldName);
        compareFieldValues(fieldName, actualValue, expectedValue);
        return this;
    }

    public PersonaInfoPage switchToPersonaInfoTab(){
        $(By.xpath("//span[text()='Персональная информация']")).click();
        return page(PersonaInfoPage.class);
    }

    public IdentityDocsPage switchToIdentityDocsTab(){
        $(By.xpath("//span[text()='Документы, удостоверяющие личность']")).click();
        return page(IdentityDocsPage.class);
    }

    public EducationDocsPage switchToEducationDocsTab(){
        $(By.xpath("//span[text()='Документы об образовании']")).click();
        return page(EducationDocsPage.class);
    }

    public StudentPersonalCasePage clickEditPersonalCaseButton(){
        $(By.xpath(editCase)).shouldBe(Condition.enabled).click();
        //ждем, когда поле станет доступным для редактирования
        String checkField = locators.getProperty("set_combobox_value_template")
                .replace("''","'Общежитие'");
        $(By.xpath(checkField)).shouldHave(Condition.enabled);
        return this;
    }

    public StudentPersonalCasePage clickCancelCaseEditButton(){
        $(By.xpath(cancelCaseEdit)).shouldBe(Condition.enabled).click();
        //ждем, когда поле станет закрытым для редактирования
        String checkField = locators.getProperty("set_combobox_value_template")
                .replace("''","'Общежитие'");
        $(By.xpath(checkField)).shouldHave(Condition.disabled);
        return this;
    }

    public StudentPersonalCasePage clickSaveCaseButton(){
        $(By.xpath(saveCase)).shouldBe(Condition.enabled).click();
        return this;
    }
}
