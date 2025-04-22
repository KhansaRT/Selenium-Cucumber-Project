package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"stepDefinitions", "utils"},
    tags = "@Login",
    plugin = {"pretty", "html:target/report/cucumber-reports.html"},
    monochrome = true
)
public class TestRunner {
}