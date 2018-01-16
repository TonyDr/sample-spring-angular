package specs;


import com.adven.concordion.extensions.exam.ExamExtension;
import org.concordion.api.AfterSpecification;
import org.concordion.api.BeforeSpecification;
import org.concordion.api.extension.Extension;
import org.concordion.api.option.ConcordionOptions;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.tony.sample.WebAppInitializer;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

@RunWith(ConcordionRunner.class)
@ConcordionOptions(declareNamespaces = {"c", "http://www.concordion.org/2007/concordion", "e", ExamExtension.NS})
public class SampleSpecs {

    private static ConfigurableApplicationContext appContext;
    @Extension
    private final ExamExtension exam = new ExamExtension().
            rest().end().
            db().end().webDriver();




    public void hasText(String text) throws Exception {
        $(By.tagName("h3")).shouldHave(text(text));
    }

    @BeforeSpecification
    public static void beforeSpecification() {
        if (appContext == null) {
            appContext = SpringApplication.run(WebAppInitializer.class, new String[]{});
            appContext.start();
        }
    }

    @AfterSpecification
    public static void afterSpecification() {
        if (appContext != null) {
            appContext.stop();
        }
    }
}


