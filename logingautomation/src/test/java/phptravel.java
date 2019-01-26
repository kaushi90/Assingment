import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

public class phptravel {
    String baseURL ="https://www.phptravels.net/login";
    String email = "user@phptravels.com";
    String password ="demouser";
    private WebDriver driver;
    SoftAssert soft = new SoftAssert(); //use soft assets here
    WebDriverWait wait;
    @BeforeTest (groups = {"Positive_Secnario"}) //before run the travelValidLoging initiate the web driver
    public void startup(){
        System.setProperty("webdriver.gecko.driver","C:\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get(baseURL);
        wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[@class='panel-heading go-text-right']")));
    }


    @Test (priority=1,groups = { "Positive_Secnario" }) //loging to the web site with valid username and password
    public void travelValidLoging()
    {
        soft.assertEquals(driver.getTitle(),"Login"); //test case 1 for check login page appearence
        driver.findElement(By.xpath(".//input[@name='username']")).sendKeys(email); //set username
        driver.findElement(By.xpath(".//input[@name='password']")).sendKeys(password); //set password
        driver.findElement(By.xpath(".//input[@id='remember-me']")).click(); //click checkbox
        driver.findElement(By.xpath(".//button[@class='btn btn-action btn-lg btn-block loginbtn']")).click(); //click login button
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//a[@href='#bookings']")));
        soft.assertEquals(driver.getTitle(),"My Account"); //Test case 2 for verify the login
        soft.assertAll();
        driver.findElement(By.xpath("/html/body/nav/div/div[2]/ul[2]/ul/li[1]/a")).click();
        driver.findElement(By.xpath("/html/body/nav/div/div[2]/ul[2]/ul/li[1]/ul/li[2]/a")).click(); //sing out
    }
    @Test (priority=3,groups = { "Negative_Secnario" }) //group and priority the method according to the execution sequence
   public void blankLogingData()
   {
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//input[@name='username']")));
       soft.assertEquals(driver.getTitle(),"Login");
       driver.findElement(By.xpath(".//input[@name='username']")).sendKeys(" "); //set blank username
       driver.findElement(By.xpath(".//input[@name='password']")).sendKeys(" "); //set blank password
       driver.findElement(By.xpath(".//button[@class='btn btn-action btn-lg btn-block loginbtn']")).click(); //click login button
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[5]/div[1]/div[1]/form/div[1]/div[2]/div")));
       soft.assertEquals(driver.findElement(By.xpath("/html/body/div[5]/div[1]/div[1]/form/div[1]/div[2]/div")).getText(),"Invalid Email or Password"); //testcase 3 to verify the validation messages
   }
    @Test (priority=2,groups = { "Negative_Secnario" })
    public  void invalidLogingData()
    {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//input[@name='username']")));
        driver.findElement(By.xpath(".//input[@name='username']")).sendKeys("kanishka"); //set invalid username
        driver.findElement(By.xpath(".//input[@name='password']")).sendKeys("1234!@#"); //set invalid password
        driver.findElement(By.xpath(".//button[@class='btn btn-action btn-lg btn-block loginbtn']")).click(); //click sing in button
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[5]/div[1]/div[1]/form/div[1]/div[2]/div")));
        soft.assertEquals(driver.findElement(By.xpath("/html/body/div[5]/div[1]/div[1]/form/div[1]/div[2]/div")).getText(),"Invalid Email or Password"); //testcase 4 to verify the validation messages
        driver.findElement(By.xpath(".//input[@name='username']")).clear(); //clear the text box
        driver.findElement(By.xpath(".//input[@name='password']")).clear(); //clear the text box
    }
   @AfterTest (groups ={"Positive_Secnario","Negative_Secnario" }) //after groups run the below method
    public void closeBrowser()
   {
       driver.quit(); //quit the firefox browser
   }
}
