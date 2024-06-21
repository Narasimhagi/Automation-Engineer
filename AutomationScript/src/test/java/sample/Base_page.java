package sample;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Base_page implements Base_constants
{
	public WebDriver driver;
	@BeforeMethod
	void openbrowser()
	{
		  driver = WebDriverManager.chromedriver().create();
	        driver.get(Url);
	        driver.manage().window().maximize();
	        driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}
	
	@AfterMethod
	void close_Browers()
	{
		driver.quit();
	}

}
