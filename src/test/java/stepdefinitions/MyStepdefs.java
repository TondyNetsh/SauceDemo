package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyStepdefs {
    public WebDriver driver;
    public WebDriverWait wait;

    @Given("User navigates to the homepage")
    public void userNavigatesToTheHomepage() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();

        driver.navigate().to("https://www.saucedemo.com");
    }

    @When("User enters {} and {}")
    public void userEntersAndPassword(String username, String password) {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL, "https://www.saucedemo.com/inventory.html");
    }

    @Then("User adds items to the cart")
    public void userAddsItemsToTheCart() {
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
    }

    @Then("User verifies number of items in the cart")
    public void userVerifiesItemsInTheCart() {
        WebElement cartIcon = driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a"));
        WebElement cartCountBadge = driver.findElement(By.className("shopping_cart_badge"));
        String cartItemCount = cartCountBadge.getText();
        int itemCount = Integer.parseInt(cartItemCount);
        Assert.assertEquals(itemCount, 2);
    }

    @Then("User view and verify items in the cart")
    public void userViewAndVerifyItemsInTheCart() {
        driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a")).click();

        String item1Name = driver.findElement(By.xpath("//*[@id=\"item_4_title_link\"]/div")).getText();
        Assert.assertEquals(item1Name, "Sauce Labs Backpack");

        WebElement priceElement = driver.findElement(By.className("inventory_item_price"));
        String priceText = priceElement.getText();
        priceText = priceText.replace("$","").trim();
        double price = Double.parseDouble(priceText);
        Assert.assertEquals(price, 29.99, 0.01);

        String item2Name = driver.findElement(By.xpath("//*[@id=\"item_0_title_link\"]/div")).getText();
        Assert.assertEquals(item2Name, "Sauce Labs Bike Light");

        WebElement item2Element = driver.findElement(By.xpath("//*[@id=\"cart_contents_container\"]/div/div[1]/div[4]/div[2]/div[2]/div"));
        String priceText2 = item2Element.getText();
        priceText2 = priceText2.replace("$","").trim();
        double price2 = Double.parseDouble(priceText2);
        Assert.assertEquals(price2, 9.99, 0.01);
    }

    @Then("User checkout the cart")
    public void userCheckoutTheCart() {
        driver.findElement(By.id("checkout")).click();
    }

    @Then("User enters checkout information")
    public void userEntersCheckoutInformation() {
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.id("continue")).click();
    }

    @Then("User verifies items are correct")
    public void userVerifiesItemsAreCorrect() {
        String checkoutItem1 = driver.findElement(By.xpath("//*[@id=\"item_4_title_link\"]/div")).getText();
        Assert.assertEquals(checkoutItem1, "Sauce Labs Backpack");

        String checkoutItem2 = driver.findElement(By.xpath("//*[@id=\"item_0_title_link\"]/div")).getText();
        Assert.assertEquals(checkoutItem2, "Sauce Labs Bike Light");
    }

    @Then("User verifies that the total price is correct")
    public void userVerifiesThatTheTotalPriceIsCorrect() {
        WebElement totalPriceElement = driver.findElement(By.className("summary_subtotal_label"));
        String totalPriceString = totalPriceElement.getText();
        totalPriceString = totalPriceString.replace("Item total: $", "").trim();
        double totalPrice = Double.parseDouble(totalPriceString);
        Assert.assertEquals(totalPrice, 39.98, 0.01);
    }

    @Then("User clicks on finish")
    public void userClicksOnFinish() {
        driver.findElement(By.id("finish")).click();
    }

    @Then("User verifies the order is complete and logout")
    public void userVerifiesTheOrderIsCompleteAndLogout() {
        String success = driver.findElement(By.className("complete-header")).getText();
        Assert.assertEquals(success, "Thank you for your order!");
    }

    @Then("User logout after completing order")
    public void userLogoutAfterCompletingOrder() {
        driver.findElement(By.id("back-to-products")).click();

        driver.findElement(By.id("react-burger-menu-btn")).click();

        WebElement sidebar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout_sidebar_link")));
        sidebar.click();

        String logoutURL = driver.getCurrentUrl();
        Assert.assertEquals(logoutURL, "https://www.saucedemo.com/");
    }
}
