package stepdefinitions;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/SauceDemo/SauceDemo.feature",
                    glue = "stepdefinitions")
public class TestRunner {
}
