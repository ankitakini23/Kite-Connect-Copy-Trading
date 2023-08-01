package com.example.copytrade.services;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.User;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

@Slf4j
@Service
public class KiteTradeLoginAutomation {

    @Value("${self.api.key}")
    String apiKey = "your_api_key";
    @Value("${self.api.secret}")
    String apiSecret = "your_api_secret_key";
    @Value("${self.user.id}")
    String userId = "your_user_id";
    @Value("${self.user.pwd}")
    String userPwd = "your_user_password";
    @Value("${self.zerodha.totp.key}")
    String zerodhaTotpKey = "your_totp_key";

    public KiteConnect login() {
        log.info("login start");
        try {
            String requestToken = getRequestToken();
            if (requestToken == null) {
                log.info("requestToken is null, exiting..");
                return null;
            }
            log.info("requestToken = {}", requestToken);
            KiteConnect kite = new KiteConnect(apiKey);
//            User user = kite.generateSession(requestToken, apiSecret);
//            kite.setAccessToken(user.accessToken);

            return kite;
//        } catch (IOException | KiteException e) {
//            log.info("Error Occurred while Login : {}", e.getMessage());
////            throw new RuntimeException(e);
//        }
        } catch (Exception e) {
            log.info("Error Occurred while Login : {}", e.getMessage());

        }
        return null;
    }

    public String getRequestToken() {
        log.info("getRequestToken start");
        // Set the path to the chromedriver executable (Make sure you have Chrome installed)
//        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");

        //OR
        // Setup ChromeDriver automatically using WebDriverManager
        WebDriverManager.chromedriver().setup();

        // Create an instance of ChromeDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Navigate to the login page
            driver.get("https://kite.trade/connect/login?api_key=" + apiKey + "&v=3");

            // Set the timeout duration (e.g., 10 seconds)
            Duration timeoutDuration = Duration.ofSeconds(10);

            // Find the login_id field and enter user_id
            WebElement login_id = new WebDriverWait(driver, timeoutDuration)
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='userid']")));
            login_id.sendKeys(userId);

            // Find the password field and enter user_pwd
            WebElement pwd = new WebDriverWait(driver, timeoutDuration)
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='password']")));
            pwd.sendKeys(userPwd);

            // Find the submit button and click it
            WebElement submit = new WebDriverWait(driver, timeoutDuration)
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='container']/div/div/div[2]/form/div[4]/button")));
            submit.click();

            // Add some sleep for demonstration purposes (not recommended for production)
            Thread.sleep(1000);

            // You can perform further actions or validations here
            String totp = TOTPGenerator.getTotp(zerodhaTotpKey);

            WebElement totpEle = new WebDriverWait(driver, timeoutDuration)
//                    .until(ExpectedConditions.elementToBeClickable(By.xpath("'//*[@id='container']/div/div/div[2]/form/div[2]/input")));
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"container\"]/div[2]/div/div/form/div[1]/input")));

            totpEle.sendKeys(totp);
//
            // Find the continue button and click it
//            WebElement continueBtn = new WebDriverWait(driver, Duration.ofSeconds(10))
////                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='container']/div/div/div[2]/form/div[3]/button")));
//                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"container\"]/div[2]/div/div/form/div[2]/button")));
//            continueBtn.click();

            // Wait for the login process to complete (e.g., redirect to the next page)
            Thread.sleep(1000);

            // Extract the request_token from the current URL
            String url = driver.getCurrentUrl();
            log.info("url = {}", url);
            String initial_token = url.split("request_token=")[1];
            String request_token = initial_token.split("&")[0];

            // Close the browser
            driver.quit();
            return request_token;
//                return null;
        } catch (Exception e) {
            log.info("Error occurred while getting Request Token : {}", e.getMessage());
//            e.printStackTrace();
        } finally {
            // Close the browser when done
            driver.quit();
        }
        return null;
    }
}
