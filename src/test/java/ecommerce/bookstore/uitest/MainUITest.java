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
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainUITest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Set up the WebDriver (in this example, using ChromeDriver)
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        // Set chrome binary path
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.setBinary("C:/Program Files/Google/Chrome Dev/Application/chrome.exe");
//        options.addArguments("--headless");  // Run in headless mode
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
    public void testMainPortal() {
        WebElement book;
        WebElement title;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Create a new account
        driver.get("http://localhost:8080/signup");
        driver.findElement(By.id("name")).sendKeys("test");
        driver.findElement(By.id("username")).sendKeys("test");
        driver.findElement(By.id("password")).sendKeys("test");
        driver.findElement(By.id("confirmpassword")).sendKeys("test");
        driver.findElement(By.id("street")).sendKeys("123 main street");
        driver.findElement(By.id("province")).sendKeys("ON");
        driver.findElement(By.id("country")).sendKeys("Canada");
        driver.findElement(By.id("zip")).sendKeys("M1V1Y9");
        driver.findElement(By.id("register")).click();

        // Login
        wait.until(ExpectedConditions.titleIs("Login"));
        driver.findElement(By.id("username")).sendKeys("test");
        driver.findElement(By.id("password")).sendKeys("test");
        driver.findElement(By.id("login")).click();

        // Wait till finished login
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logoutButton")));

        // Check first book
        book = driver.findElement(By.className("book"));
        title = book.findElement(By.className("card-title"));
        assertEquals(title.getText(), "The Midnight Library", "Expected book title to be The Midnight Library");

        // Test pagination
        driver.findElement(By.id("nextBtn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("book")));
        book = driver.findElement(By.className("book"));
        title = book.findElement(By.className("card-title"));
        assertEquals(title.getText(), "Frankenstein", "Expected book title to be Frankenstein");
        driver.findElement(By.id("prevBtn")).click();

        // Change categories: Find and click the radio button element using its attributes
        WebElement textbookCategory = driver.findElement(By.cssSelector("input.form-check-input[value='TEXTBOOK'][type='radio']"));
        textbookCategory.click();
        wait.until(ExpectedConditions.elementToBeSelected(textbookCategory));
        book = driver.findElement(By.className("book"));
        title = book.findElement(By.className("card-title"));
        assertEquals(title.getText(), "Java: A Beginners Guide, Eighth Edition", "Expected book title to be Java: A Beginners Guide, Eighth Edition");
        driver.findElement(By.cssSelector("input.form-check-input[value='ALL'][type='radio']")).click();

        // Search for a book
        driver.findElement(By.id("search")).sendKeys("A Promised Land");
        driver.findElement(By.id("searchSubmit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("book")));
        book = driver.findElement(By.className("book"));
        title = book.findElement(By.className("card-title"));
        assertEquals(title.getText(), "A Promised Land", "Expected book title to be A Promised Land");
    }
}
