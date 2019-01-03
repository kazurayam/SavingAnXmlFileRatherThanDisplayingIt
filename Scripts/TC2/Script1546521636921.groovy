import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import java.nio.file.Files as Files
import java.nio.file.Path as Path
import java.nio.file.Paths as Paths
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.RestRequestObjectBuilder as RestRequestObjectBuilder
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')

WebUI.navigateToUrl('https://kazurayam.github.io/SavingAnXmlFileRatherThanDisplayingIt/')

WebUI.verifyElementPresent(findTestObject('Page_Sample/div_resultDiv'), 10)

// look up the href attribute of the XML in the 1st row of the table
String href = 
	WebUI.getAttribute(
		findTestObject('Page_Sample/nth_tr_anchor_to_xml', ['row':'1']),   // interpolate tr[${row}] => tr[1]
		'href')

WebUI.comment("href is ${href}")

WebUI.closeBrowser()

// Create a new GET object using builder
def builder = new RestRequestObjectBuilder()
def requestObject = builder
	.withRestRequestMethod("GET")
	.withRestUrl(href)            // here we specify the URL found in the web site
	.build()
	
// Send a request'
def response = WS.sendRequest(requestObject)

// Verify if the response from the URL returns the 200 status code'
WS.verifyResponseStatusCode(response, 200)

// Get the content string
def content = response.getResponseBodyContent()

// prepare output directory
Path projectdir = Paths.get(RunConfiguration.getProjectDir())
Path outputdir = projectdir.resolve("tmp")
Files.createDirectories(outputdir)
Path file = outputdir.resolve("sample.xml")

// save XML into file
file.toFile().write(content)


