package PageObjects;

import Common.RegressionTest;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.logging.Logger;

import static Common.RegressionTest.locators;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class RmanpoMainPage extends RegressionTest {
    private static Logger logger = Logger.getLogger(RmanpoMainPage.class.getSimpleName());
    String RmanpoDPOButton = locators.getProperty("rmanpo_dpo_button");

    public RmanpoDpoMainPage clickRmanpoDpoButton(){
        $(By.xpath(RmanpoDPOButton)).click();
        return page(RmanpoDpoMainPage.class);
    }
}
