package ecommerce.bookstore.uitest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookItemUITest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Set up the WebDriver (in this example, using ChromeDriver)
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        // Set chrome binary path
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.setBinary("C:/Program Files/Google/Chrome Dev/Application/chrome.exe");
        // options.addArguments("--headless");  // Run in headless mode
        driver = new ChromeDriver(options);
        // Maximize the browser window to make it fullscreen
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        // Close the WebDriver and logout
        driver.get("http://localhost:8080");
        WebElement logoutBtn = driver.findElement(By.id("logoutButton"));
        logoutBtn.click();
        driver.quit();
    }

    @Test
    public void testBookItem() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // login
        driver.get("http://localhost:8080/login");
        WebElement username = driver.findElement(By.id("username"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement loginBtn = driver.findElement(By.id("login"));

        username.sendKeys("admin");
        password.sendKeys("admin");
        loginBtn.click();

        // Wait till finished login
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logoutButton")));

        // Open the Book page
        driver.get("http://localhost:8080/book/9781786892706");

        // Assert book item information
        WebElement book = driver.findElement(By.className("card-body"));
        String expectedTitle = "The Midnight Library";
        String expectedAuthor = "By: Matt Haig";
        String expectedPrice = "Price: $30.54";
        String actualTitle = book.findElement(By.className("card-title")).getText();
        String actualAuthor = book.findElement(By.className("card-text")).getText();
        String actualPrice = book.findElement(By.className("price")).getText();

        assertEquals(expectedTitle, actualTitle, "Expected title to be: " + actualTitle);
        assertEquals(expectedAuthor, actualAuthor, "Expected author to be: " + actualAuthor);
        assertEquals(expectedPrice, actualPrice, "Expected price to be: " + expectedPrice);

        // Add book to cart
        WebElement addToCartBtn = driver.findElement(By.id("addToCart"));
        WebElement cartItemCount = driver.findElement(By.id("cartItemCount"));
        String inCartText = "Add to Cart";
        String outCartText = "Remove from Cart";

        assertEquals(cartItemCount.getText(), "0", "Expected cart item count to be 0");
        assertEquals(addToCartBtn.getText(), inCartText, "Expected button text ot be: " + inCartText);

        addToCartBtn.click();
        wait.until(ExpectedConditions.textToBePresentInElement(cartItemCount, "1"));

        assertEquals(cartItemCount.getText(), "1", "Expected cart item count to be 1");
        assertEquals(addToCartBtn.getText(), outCartText, "Expected button text ot be: " + outCartText);
        addToCartBtn.click();

        // Add review
        WebElement reviewArea = driver.findElement(By.id("reviewarea"));
        WebElement ratingArea = driver.findElement(By.id("ratingarea"));
        WebElement submitReviewBtn = driver.findElement(By.id("submitReviewBtn"));
        WebElement pagination = driver.findElement(By.id("pagination"));
        String reviewValue = "test";
        String ratingValue = "5";

        reviewArea.sendKeys(reviewValue);
        ratingArea.sendKeys(ratingValue);
        submitReviewBtn.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("book-review")));

        List<WebElement> newReview = driver.findElement(By.className("book-review")).findElements(By.tagName("p"));
        assertEquals(newReview.get(0).getText(), "Review: " + reviewValue, "Expected review value to be: " + reviewValue);
        assertEquals(newReview.get(1).getText(), "Rating: " + ratingValue + " ★", "Expected rating value to be: " + ratingValue);
    }
}
