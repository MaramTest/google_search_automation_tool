import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.interactions.Actions;
import javax.lang.model.element.Element;


public class SearchGoogle {
    ChromeDriver driver;
    @BeforeTest
    public void OpenURL(){
        WebDriverManager.chromedriver().setup();
     driver=new ChromeDriver();
     driver.navigate().to("https://www.google.com/");
     driver.manage().window().maximize();
     driver.findElement(By.xpath("//*[@id=\"L2AGLb\"]/div")).click();
    }

    @Test
    public void searchfield(){
        // Initialize variables to track scroll count and result count
        int scrollCount = 0;
        int resultCount = 0;

        try {

            WebElement srch=driver.findElement(By.id("APjFqb"));
            srch.sendKeys("Mercedes A class");
            srch.sendKeys(Keys.ENTER);

            Actions actions = new Actions(driver);
            while (true) {
                actions.sendKeys(Keys.END).perform();
                scrollCount++;
                Thread.sleep(1000);
                var searchResults = driver.findElements(By.cssSelector("div.g"));

                for (WebElement result : searchResults) {

                    resultCount++;

                    if (result.getText().contains("horsepower") ||result.getText().contains("specifications")) {
                        System.out.println("Found relevant information on result #" + resultCount);
                        System.out.println("Number of scrolls to reach this result: " + scrollCount);
                       driver.quit();
                        System.exit(0);
                    }

                }
                // Check if the "Show more" button exists and click it if found
                WebElement showMoreButton = driver.findElement(By.xpath("//span[text()='More results']"));
                if (showMoreButton.isDisplayed()) {
                    actions.moveToElement(showMoreButton).click().perform();
                    scrollCount++;
                }
               else {
                    break;
                }
            }
        }
        catch (Exception e){
            System.out.println("Some thing went wrong");
        }
    }


}
