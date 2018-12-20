import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.RestRequestObjectBuilder
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

def anchorElement = findTestObject('Object Repository/Page_RSS Feeds - FIFA.com/a_FIFA World Cup Russia 2018')

// Open site 
WebUI.openBrowser('')
WebUI.setViewPortSize(800, 600)
WebUI.navigateToUrl('http://static.fifa.com/rss-feeds/index.html')
WebUI.scrollToElement(anchorElement, 3, FailureHandling.STOP_ON_FAILURE)
WebUI.verifyElementPresent(anchorElement, 10, FailureHandling.STOP_ON_FAILURE)
// identify URL of the XML document
def href = WebUI.getAttribute(anchorElement, "href")
// will see href="http://static.fifa.com/worldcup/news/rss.xml"
println "href=${href}"
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
Path file = outputdir.resolve("FIFA_World_Cup_Russia_2018.rss.xml")

// save XML into file
file.toFile().write(content)


