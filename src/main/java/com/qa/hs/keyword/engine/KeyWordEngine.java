package com.qa.hs.keyword.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.hs.keyword.base.Base;

/**
 * 
 * @author Anuradha
 * Code for Execution Engine 
 * Accessing Execution data in excel file
 *
 */
public class KeyWordEngine {

	public WebDriver driver;
	public Properties prop;

	public static Workbook book;
	public static Sheet sheet;

	public Base base;
	WebElement element;

	public final String SCENARIO_SHEET_PATH = "D:\\Selenium_workspace\\KeywordDrivenHubSpotLocators\\"
			+ "src\\main\\java\\com\\qa\\hs\\keyword\\scenarios\\hubspot_scenarios.xlsx";

	public void startExecution(String sheetName) {

		FileInputStream file = null;

		try {
			file = new FileInputStream(SCENARIO_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sheet = book.getSheet(sheetName);

		int k = 0;
		for(int i = 0; i < sheet.getLastRowNum(); i++) {

			try {

				//i will start from second row where actual data starts
				//k will point to second column to get locator value
				String locatorType = sheet.getRow(i + 1).getCell(k + 1).toString().trim(); // id
				String locatorValue = sheet.getRow(i + 1).getCell(k + 2).toString().trim(); // username

				//get data for action and value at third and fourth column in same row
				String action = sheet.getRow(i + 1).getCell(k + 3).toString().trim(); // sendkeys
				String value = sheet.getRow(i + 1).getCell(k + 4).toString().trim(); // naveenanimation20@gmail.com

				//switch based on action
				switch (action) {
				case "open browser":
					base = new Base();
					prop = base.initProperties();

					//if browser value is empty or NA in excel file, pick the browser from properties file
					//else pick browser value from excel file
					if(value.isEmpty() || value.equalsIgnoreCase("NA")) {
						System.out.println("browser empty"+prop.getProperty("browser"));
						driver = base.initDriver(prop.getProperty("browser"));
					} else {
						System.out.println("browser not empty"+value);
						driver = base.initDriver(value);
					}
					break;
				case "enter url":
					if(value.isEmpty() || value.equalsIgnoreCase("NA")) {
						System.out.println("url empty"+prop.getProperty("url"));
						driver.get(prop.getProperty("url"));
					} else {
						System.out.println("url not empty"+value);
						driver.get(value);
					}
					Thread.sleep(10000);
					break;
				case "quit":
					driver.quit();
					break;

				default:
					break;
				}

				System.out.println("switch for locator = "+ locatorType);
				System.out.println("switch for locator value = "+ locatorValue);
				Thread.sleep(7000);
				if(!(locatorType.equalsIgnoreCase("NA"))) {
					
					//switch based on locator
					switch (locatorType) {
					case "id":
						element = driver.findElement(By.id(locatorValue));
						//either sendkeys or click based on action
						if(action.equalsIgnoreCase("sendkeys")) {
							element.clear();
							element.sendKeys(value);
						} else if(action.equalsIgnoreCase("click")) {
							element.click();
						} else if(action.equalsIgnoreCase("isDisplayed")) {
							element.isDisplayed();						
						} else if(action.equalsIgnoreCase("getText")) {
							String elementText = element.getText();
							System.out.println("text from element: " + elementText);
						}
						locatorType = null;
						break;
						
					case "name":
						element = driver.findElement(By.name(locatorValue));
						//either sendkeys or click based on action
						if(action.equalsIgnoreCase("sendkeys")) {
							element.clear();
							element.sendKeys(value);
						} else if(action.equalsIgnoreCase("click")) {
							element.click();
						} else if(action.equalsIgnoreCase("isDisplayed")) {
							element.isDisplayed();						
						} else if(action.equalsIgnoreCase("getText")) {
							String elementText = element.getText();
							System.out.println("text from element: " + elementText);
						}
						locatorType = null;
						break;
						
					case "xpath":
						element = driver.findElement(By.xpath(locatorValue));
						//either sendkeys or click based on action
						if(action.equalsIgnoreCase("sendkeys")) {
							element.clear();
							element.sendKeys(value);
						} else if(action.equalsIgnoreCase("click")) {
							element.click();
						} else if(action.equalsIgnoreCase("isDisplayed")) {
							element.isDisplayed();						
						}else if(action.equalsIgnoreCase("getText")) {
							String elementText = element.getText();
							System.out.println("text from element: " + elementText);
						}
						break;
						
					case "className":
						element = driver.findElement(By.className(locatorValue));
						//either sendkeys or click based on action
						if(action.equalsIgnoreCase("sendkeys")) {
							element.clear();
							element.sendKeys(value);
						} else if(action.equalsIgnoreCase("click")) {
							element.click();
						} else if(action.equalsIgnoreCase("isDisplayed")) {
							element.isDisplayed();						
						} else if(action.equalsIgnoreCase("getText")) {
							String elementText = element.getText();
							System.out.println("text from element: " + elementText);
						}
						break;
						
					case "cssSelector":
						element = driver.findElement(By.cssSelector(locatorValue));
						//either sendkeys or click based on action
						if(action.equalsIgnoreCase("sendkeys")) {
							element.clear();
							element.sendKeys(value);
						} else if(action.equalsIgnoreCase("click")) {
							element.click();
						} else if(action.equalsIgnoreCase("isDisplayed")) {
							element.isDisplayed();						
						} else if(action.equalsIgnoreCase("getText")) {
							String elementText = element.getText();
							System.out.println("text from element: " + elementText);
						}
						locatorType = null;
						break;
						
					case "linkText":
						element = driver.findElement(By.linkText(locatorValue));
						element.click();
						locatorType = null;
						break;
						
					case "partialLinkText":
						element = driver.findElement(By.partialLinkText(locatorValue));
						element.click();
						locatorType = null;
						break;
						
					default:
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
