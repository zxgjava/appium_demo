package cn.agilean;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WifiAndroidTest {

    private AppiumDriver<WebElement> driver;

    @Before
    public void setUp() throws Exception {
        System.out.println("test case method start");
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "apps");
        File app = new File(appDir, "PINGAN_WIFI_ANDROID.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName","Android Emulator");
        capabilities.setCapability("platformVersion", "4.4");
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("appPackage", "com.pingan.pinganwifi");
        capabilities.setCapability("appActivity", ".loading.LoadingActivity");
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("test case method end");
        driver.quit();
    }

    @Test
    public void wifiDemo(){
        WebDriverWait wait = new WebDriverWait(driver, 5);

        WebElement el = driver.findElement(By.name("立即体验"));
        assertEquals("立即体验", el.getText());
        el.click();

        el = driver.findElement(By.name("我"));
        assertEquals("我", el.getText());
        el.click();

        el = driver.findElement(By.name("注册"));
        assertEquals("注册", el.getText());
        el.click();

        el = driver.findElement(By.name("登录"));
        assertEquals("登录", el.getText());
        el.click();

        // find user-name input field
        WebElement userNameInput = driver.findElement(By.id("login_phone_edit"));
        wait.until(ExpectedConditions.visibilityOf(userNameInput));

        // type user-name in input field
        userNameInput.clear();
        userNameInput.sendKeys("15834064949");
        driver.findElement(By.id("login_password_edit")).sendKeys("15834064949");

        // submit login form
        driver.findElement(By.name("立即登录")).click();

        List<WebElement> els = driver.findElementsByClassName("android.widget.TextView");
        System.out.println("els:"+els.get(0).getText());
        wait.until(ExpectedConditions.visibilityOf(els.get(0)));
        //assertEquals("Activity", els.get(2).getText());
    }
    @Test
    public void testWifiListView(){
        WebDriverWait wait = new WebDriverWait(driver, 5);

        WebElement el = driver.findElement(By.name("立即体验"));
        assertEquals("立即体验", el.getText());
        el.click();

        el = driver.findElement(By.name("WiFi"));
        assertEquals("WiFi", el.getText());
        el.click();

        el = driver.findElement(By.className("android.widget.ListView"));
        List<WebElement> els = el.findElements(By.className("android.widget.RelativeLayout"));
        wait.until(ExpectedConditions.visibilityOf(el));
        System.out.println("wifiList.size():"+els.size());
        assertTrue(els.size() > 2);
    }
    @Test
    public void testFindNewsListView() {
        WebDriverWait wait = new WebDriverWait(driver, 5);

        WebElement el = driver.findElement(By.name("立即体验"));
        assertEquals("立即体验", el.getText());
        el.click();

        el = driver.findElement(By.name("发现"));
        assertEquals("发现", el.getText());
        el.click();

        el = driver.findElement(By.className("android.widget.ListView"));
        List<WebElement> els = el.findElements(By.className("android.widget.RelativeLayout"));
        wait.until(ExpectedConditions.visibilityOf(el));
        System.out.println("newsList.size():"+els.size());
        assertTrue(els.size() > 0);
    }
}
