package com.scb;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PumaProject {

	WebDriver driver = null;
	String expTitle = "Buy Sports T-Shirts, Tracks, Running Shoes and Accessories Online - in.puma.com";
	String driverloc = "C:\\Users\\glsndp\\Desktop\\Drivers\\chromedriver.exe";
	String baseUrl = "https://in.puma.com";

	@Test
	public void verifyMethod() throws InterruptedException {
		driver.get(baseUrl);
		Assert.assertEquals(driver.getTitle(), expTitle);
		WebDriverWait wait = new WebDriverWait(driver, 10);

		WebElement element = driver.findElement(By.xpath("//div[@id='header-nav']/ul/li[1]/a"));
		WebElement element1 = driver.findElement(By.xpath("//div[@class='digimeg-nav-chunk']/ul/li/a[text()='Running']"));

		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
		wait.until(ExpectedConditions.elementToBeClickable(element1));
		action.moveToElement(element1).click().build().perform();

		driver.findElement(By.xpath("//div[@class='category-products']/ul/li[2]/a")).click();
		
		//Navigating between windows 
		Set<String> set = driver.getWindowHandles();
		for (String string : set) {
			driver.switchTo().window(string);
			String str = driver.getTitle();
			if (!str.contains("Puma mens running shoes")) {
				break;
			}
		}
		
		//Select Product Size
		WebElement productSize = driver.findElement(By.xpath("//div[@class='product-size-click-btn']"));
		action.moveToElement(productSize).click().build().perform();
		Thread.sleep(3000);
		WebElement seleSize = driver.findElement(By.xpath("//a[@title='7']"));
		action.moveToElement(seleSize).click().build().perform();
		Thread.sleep(3000);
		//Add to Cart Button, it will wait till the element is clickable
		WebElement addToCart = driver.findElement(By.xpath("//button[@title='Add to Cart']"));		
		wait.until(ExpectedConditions.elementToBeClickable(addToCart));
		action.moveToElement(addToCart).click().build().perform();
							
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[@class='product-cart-info']/dl/dd[2]")));
		String size = driver.findElement(By.xpath("//td[@class='product-cart-info']/dl/dd[2]")).getText().trim();
		Assert.assertEquals(size, "7");

		WebElement actSize = driver.findElement(By.xpath("//td[@class='product-cart-actions']/div/select"));
		Select selectSize = new Select(actSize);
		String quantity = selectSize.getFirstSelectedOption().getText().trim();
		Assert.assertEquals(quantity, "1");

	}

	@BeforeTest
	public void createDriver() {
		System.setProperty("webdriver.chrome.driver",driverloc);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("useAutomationExtension", false);

		driver = new ChromeDriver(options);
	}
	
	@AfterTest
	public void closeBroswer(){
		driver.quit();
	}

}
