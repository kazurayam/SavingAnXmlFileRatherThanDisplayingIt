import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import com.kms.katalon.core.testobject.RestRequestObjectBuilder
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

/**
 *  here we assume to receive 2 parameters
 *
 * @param href (java.lang.String) URL of XML file to download
 * @param outfile (java.lang.String) path of the file into which the XML is saved
 */
Objects.requireNonNull(href)
Objects.requireNonNull(outfile)

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

Path file = Paths.get(outfile)

// prepare output directory
Files.createDirectories(file.getParent())

// save XML into file
file.toFile().write(content)