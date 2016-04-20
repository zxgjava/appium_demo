package cn.agilean;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class AppiumExampleDemo {
	private WebDriver driver = null;
 
	@Before
	public void setup() {
		File appDir = new File("..//TestedAndroidApp//bin//");
		File app = new File(appDir, "TestedAndroidApp.apk");
 
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		capabilities.setCapability(CapabilityType.VERSION, "4.2");
		capabilities.setCapability(CapabilityType.PLATFORM, "WINDOWS");
//		capabilities.setCapability(CapabilityConstants.DEVICE, "android");
//		capabilities.setCapability(CapabilityConstants.APP_PACKAGE, "com.example.android");
//		capabilities.setCapability(CapabilityConstants.APP_ACTIVITY, "MainActivity");
//		capabilities.setCapability(CapabilityConstants.APP, app.getAbsolutePath());
 
		try {
			driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
 
		driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
 
	}
 
	@Test
	public void appiumExampleTest() throws Exception {
		// find button with label or content-description "Button 1"
		WebElement button=driver.findElement(By.name("Button 1"));
		// click on button and start second Activity
		button.click();
 
		// we are on second screen now
		// check if second screen contains TextView with text “Activity2”
		driver.findElement(By.name("Activity2"));
 
		// click back button
		HashMap<String, Integer> keycode = new HashMap<String, Integer>();
		keycode.put("keycode", 4);
		((JavascriptExecutor) driver).executeScript("mobile: keyevent", keycode);
 
		// we are again in main activity
		driver.findElement(By.name("Button1"));
	}
 
	@After
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
 
}