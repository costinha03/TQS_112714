package TQS;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookTestCucumber {
    private ChromeDriver driver;

    @Given("the user is on the book store homepage")
    public void the_user_is_on_the_book_store_homepage() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://cover-bookstore.onrender.com/");
        driver.manage().window().maximize();
    }

    @When("the user searches for {string}")
    public void the_user_searches_for(String bookName) {
        WebElement search_box = driver.findElement(By.className("Navbar_searchBarInput__w8FwI"));
        search_box.sendKeys(bookName);
        WebElement search_button = driver.findElement(By.className("Navbar_searchBtn__26UF_"));
        search_button.click();
    }

    @Then("the user should see {string}")
    public void the_user_should_see(String expectedTitle) {
        WebElement book_title = driver.findElement(By.className("SearchList_bookTitle__1wo4a"));
        assertEquals(expectedTitle, book_title.getText());

    }
}
