package cn.agilean;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class LoginDemo {
	private WebDriver driver = null;
 
	@Before
	public void setup() {
		File appDir = new File("..//TestedAndroidApp//bin//");
		File app = new File(appDir, "TestedAndroidApp.apk");
 
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		capabilities.setCapability(CapabilityType.PLATFORM, "WINDOWS");
		capabilities.setCapability("device", "selendroid");
//		capabilities.setCapability(CapabilityConstants.APP_PACKAGE, "com.example.android");
//		capabilities.setCapability(CapabilityConstants.APP_ACTIVITY, "LoginActivity");
//		capabilities.setCapability(CapabilityConstants.APP, app.getAbsolutePath());
 
		try {
			driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
 
	}
 
	@Test
	public void loginTest() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 10);
 
		// this is important part.
		driver.switchTo().window("WEBVIEW");
 
		// find user-name input field
		WebElement userNameInput = driver.findElement(By.id("input_user_name"));
		wait.until(ExpectedConditions.visibilityOf(userNameInput));
 
		// type user-name in input field
		userNameInput.clear();
		userNameInput.sendKeys("android1@example.com");
		driver.findElement(By.name("password")).sendKeys("password");
 
		// submit login form
		driver.findElement(By.name("login")).click();
 
		WebElement confirmButton = driver.findElement(By.name("grant"));
		wait.until(ExpectedConditions.visibilityOf(confirmButton));
		confirmButton.click();
 
		// we are now logged in app and we proceed with native app
		driver.switchTo().window("NATIVE_APP");
 
		// find button with label "button1".
		driver.findElement(By.name("button1"));
	}
 
	@After
	public void tearDown() {
		driver.quit();
	}
 
}