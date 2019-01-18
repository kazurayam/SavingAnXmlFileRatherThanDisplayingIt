import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.nio.file.Path as Path
import java.nio.file.Paths as Paths

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')
WebUI.navigateToUrl('https://kazurayam.github.io/SavingAnXmlFileRatherThanDisplayingIt/')
WebUI.verifyElementPresent(findTestObject('Page_Sample/div_resultDiv'), 10)

// prepare output directory
Path projectdir = Paths.get(RunConfiguration.getProjectDir())
Path outputdir = projectdir.resolve("tmp")


// grasp <tr> elements : 1st way; using Katalon API with a TestObject constructed beforehand 
List<WebElement> trElements1 = WebUI.findWebElements(
	findTestObject("Page_Sample/div_resultDiv.table.tbody.tr"), 10)
println "trElements1.size()=${trElements1.size()}"

// grasp <tr> elements : 2nd way; using Katalon API with a TestObject constructed on the fly
List<WebElement> trElements2 = WebUI.findWebElements(
	new TestObject().addProperty("xpath", ConditionType.EQUALS,
							"//div[@id='resultDiv']/table/tbody/tr"), 10)
println "trElements2.size()=${trElements2.size()}"

// grasp <tr> elements : 3rd way; using WebDriver's native API
WebDriver driver = DriverFactory.getWebDriver()
List<WebElement> trElements3 = driver.findElements(By.xpath("//div[@id='resultDiv']/table/tbody/tr"))
println "trElements3.size()=${trElements3.size()}"


WebUI.closeBrowser()
