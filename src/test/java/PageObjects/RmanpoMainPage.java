package PageObjects;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.page;

public class RmanpoMainPage {
    private static Logger logger = Logger.getLogger(RmanpoMainPage.class.getSimpleName());

    @FindBy(how = How.XPATH, using = "//div[contains(@class,'header') and text()='РМАНПО']/following-sibling::div/div/div/button[text()='ДПО']")
    private SelenideElement RmanpoDpoButton;

    public RmanpoDpoMainPage clickRmanpoDpoButton(){
        RmanpoDpoButton.click();
        return page(RmanpoDpoMainPage.class);
    }
}
