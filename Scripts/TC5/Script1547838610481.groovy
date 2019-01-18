import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')
WebUI.navigateToUrl('https://kazurayam.github.io/SavingAnXmlFileRatherThanDisplayingIt/')
WebUI.verifyElementPresent(findTestObject('Page_Sample/div_resultDiv'), 10)

// grasp <tr> elements : 3rd way; using WebDriver's native API
WebDriver driver = DriverFactory.getWebDriver()
WebElement webElement = driver.findElement(By.xpath("//div[@id='resultDiv']/table/tbody/tr[1]/td[1]"))
String content1 = webElement.getText()
println "content1=${content1}"

// get text of row 1- column 1 : by Katalon API
String content2 = WebUI.getText(
	new TestObject().addProperty("xpath", ConditionType.EQUALS,
							"//div[@id='resultDiv']/table/tbody/tr[1]/td[1]"))
println "content2=${content2}"

WebUI.closeBrowser()
