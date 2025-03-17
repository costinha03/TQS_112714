package TQS;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.slf4j.LoggerFactory.getLogger;
@ExtendWith(SeleniumJupiter.class)
public class BookTest {
    static final Logger log = getLogger(lookup().lookupClass());
    @Test
    void bookStoreTest(ChromeDriver driver){
        driver.get("https://cover-bookstore.onrender.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(By.cssSelector("[data-testid='book-search-input']"));
        WebElement search_box = driver.findElement(By.className("Navbar_searchBarInput__w8FwI"));
        search_box.sendKeys("Harry Potter");
        WebElement search_button = driver.findElement(By.className("Navbar_searchBtn__26UF_"));
        search_button.click();
        // use link text
        WebElement book = driver.findElement(By.className("SearchList_bookTitle__1wo4a"));
        //check
        book.click();
        WebElement book_title = driver.findElement(By.className("SearchList_bookTitle__1wo4a"));
        assertThat(book_title.getText()).isEqualTo("Harry Potter and the Sorcerer's Stone");



    }
}
