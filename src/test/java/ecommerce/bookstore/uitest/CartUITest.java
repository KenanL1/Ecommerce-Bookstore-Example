package ecommerce.bookstore.uitest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartUITest {
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

        // login
        driver.get("http://localhost:8080/login");
        WebElement username = driver.findElement(By.id("username"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement loginBtn = driver.findElement(By.id("login"));

        username.sendKeys("admin");
        password.sendKeys("admin");
        loginBtn.click();
    }

    @AfterEach
    public void tearDown() {
        // Close the WebDriver and logout
        driver.get("http://localhost:8080");
        WebElement logoutBtn = driver.findElement(By.id("logoutButton"));
        logoutBtn.click();
//        driver.quit();
    }

    @Test
    public void testCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait till finished login
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logoutButton")));

        // Add book to cart
        driver.get("http://localhost:8080/book/9781786892706");
        WebElement cartItemCount = driver.findElement(By.id("cartItemCount"));
        assertEquals(cartItemCount.getText(), "0", "Expected cart item count to be 0");

        driver.findElement(By.id("addToCart")).click();

        wait.until(ExpectedConditions.textToBePresentInElement(cartItemCount, "1"));
        assertEquals(cartItemCount.getText(), "1", "Expected cart item count to be 1");

        // Go to cart
        driver.findElement(By.id("cartButton")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart-table")));
        WebElement cartTable = driver.findElement(By.className("cart-table"));
        WebElement book = cartTable.findElement(By.className("book"));
        WebElement totalPrice = driver.findElement(By.id("totalPrice"));
        String actualTitle = book.findElement(By.tagName("td")).getText();
        String expectedTitle = "The Midnight Library";
        String expectedTotalPrice = "30.54";

        assertEquals(expectedTitle, actualTitle, "Expected book title: " + expectedTitle);
        assertEquals(expectedTotalPrice, totalPrice.getText(), "Expected total price: " + expectedTotalPrice);

        // Change quantity
        WebElement itemQuantity = book.findElement(By.className("itemQuantity"));
        itemQuantity.clear();
        itemQuantity.sendKeys("2");
        itemQuantity.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.textToBePresentInElement(totalPrice, "61.08"));
        assertEquals("61.08", totalPrice.getText(), "Expected total price: " + expectedTotalPrice);

        // Clear cart
        driver.findElement(By.id("clearCart")).click();
        wait.until(ExpectedConditions.stalenessOf(book));
        assertTrue(driver.findElements(By.className("book")).isEmpty());
    }
}
