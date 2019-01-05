import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.nio.file.Path as Path
import java.nio.file.Paths as Paths

import org.openqa.selenium.WebElement

import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')
WebUI.navigateToUrl('https://kazurayam.github.io/SavingAnXmlFileRatherThanDisplayingIt/')
WebUI.verifyElementPresent(findTestObject('Page_Sample/div_resultDiv'), 10)

// prepare output directory
Path projectdir = Paths.get(RunConfiguration.getProjectDir())
Path outputdir = projectdir.resolve("tmp")

// grasp <tr> elements
List<WebElement> trElements = WebUI.findWebElements(
	new TestObject().addProperty("xpath", ConditionType.EQUALS, 
							"//div[@id='resultDiv']/table/tbody/tr"), 10)

for (int i = 0; i < trElements.size(); i++) {
	// grasp <a> elements to XML
	List<WebElement> anchorElements = WebUI.findWebElements(
		new TestObject().addProperty("xpath", ConditionType.EQUALS,
						"//div[@id='resultDiv']/table/tbody/tr[${i+1}]/td[6]/a"), 10)
	for (int j = 0; j < anchorElements.size(); j++) {
		String href = anchorElements.get(j).getAttribute("href")
		// determin the file location
		Path outfile = outputdir.resolve("sample(${i},${j}).xml")
		WebUI.comment("(${i},${j}) href=${href} outfile=${outfile.toString()}")
		// download XML and save it into file
		WebUI.callTestCase(findTestCase("modules/DownloadXML"),
					["href":href, "outfile": outfile.toString()],
					FailureHandling.OPTIONAL)
	}
}
WebUI.closeBrowser()
