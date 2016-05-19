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
import java.util.NoSuchElementException;
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

    //@Test
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

        wifiLogin();

        List<WebElement> els = driver.findElementsByClassName("android.widget.TextView");
        System.out.println("els:"+els.get(0).getText());
        wait.until(ExpectedConditions.visibilityOf(els.get(0)));
        //assertEquals("Activity", els.get(2).getText());
    }
    //@Test
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
    //@Test
    public void testFindNewsListView() {
        //检查是否有可用的连接
        List<WebElement> els = wifiListView();
        assertTrue(els.size() > 2);
    }

    @Test
    public void testConnectWifi() {
        String connetStr = "OpenWrt";
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //检查是否有可用的连接
        List<WebElement> els = wifiListView();
        assertTrue(els.size() > 2);
        if (els.size() > 2) {
            //开始连接
            List<WebElement> list = null;
            WebElement el = null;
            list = driver.findElements(By.className("android.widget.TextView"));
            for (WebElement e : list) {
                if("断开".equals(e.getText())) {
                    e.click();
                    break;
                }
            }

            List<WebElement> listFirst = driver.findElements(By.id("ssid"));
            for (int i = 0; i < listFirst.size(); i++) {
                el = listFirst.get(i);
                if (connetStr.equals(el.getText())){
                    el.click();
                    break;
                }
            }

            //wait.until(ExpectedConditions.visibilityOf(el));
            el = driver.findElement(By.name("登录"));
            el.click();
            if(el != null) {
                wifiLogin();
            }

            List<WebElement> listSecond = driver.findElements(By.id("ssid"));
            for (int i = 0; i < listSecond.size(); i++) {
                el = listSecond.get(i);
                if (connetStr.equals(el.getText())){
                    el.click();
                    break;
                }
            }

            el = driver.findElement(By.id("connect_view"));
            wait.until(ExpectedConditions.visibilityOf(el));

//            try {
//                el = driver.findElement(By.name("重试"));
//
//                while (el != null) {
//                    el.click();
//                }
//            } catch (NoSuchElementException e){
//                System.out.print("没有重试按钮");
//            }

            el = driver.findElement(By.name("恭喜您已免费上网"));
            if(el != null) {
                el = driver.findElement(By.id("activity_frame_title_btn_left"));
                el.click();
            }

            el = driver.findElement(By.className("android.widget.ListView"));
            wait.until(ExpectedConditions.visibilityOf(el));

            el = driver.findElement(By.id("connect_status_text"));
            wait.until(ExpectedConditions.visibilityOf(el));
            assertEquals(connetStr, el.getText());

            //恭喜您已免费上网
//            com.pingan.pinganwifi:id/click_button
            //connect_status_text
            //android.widget.TextView
            //com.pingan.pinganwifi:id/connect_view
            //com.pingan.pinganwifi:id/activity_frame_title_btn_left

        }
    }

    private List<WebElement> wifiListView(){
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
        return els;
    }

    private void wifiLogin() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        // find user-name input field
        WebElement userNameInput = driver.findElement(By.id("login_phone_edit"));
        wait.until(ExpectedConditions.visibilityOf(userNameInput));

        // type user-name in input field
        userNameInput.clear();
        userNameInput.sendKeys("15834064949");
        driver.findElement(By.id("login_password_edit")).sendKeys("15834064949");

        // submit login form
        driver.findElement(By.name("立即登录")).click();
    }
}
