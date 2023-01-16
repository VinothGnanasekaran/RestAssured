package resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Utils {

	RequestSpecification reqSB;
	ResponseSpecification respSB;
	private PrintStream logFile;

	public static String getGlobalValues(String key) throws IOException {
		FileInputStream fis = new FileInputStream("D:\\Automation\\Git_Eclipse\\RestAssured_RSA\\APIFramework\\src\\test\\java\\resources\\Global.properties"); //Helps to read the data from the file in stored location
		Properties prop = new Properties();
		prop.load(fis); //Property obj should be loaded with FIS obji to read the file content into Prop
		return prop.getProperty(key);
	}
	
	public RequestSpecification requestSpec() throws IOException {
		//Build Request Spec
		logFile = new PrintStream(new FileOutputStream("LogFileReport.txt")); //FileOutputSteam - creates new file and writes during output, stores to printsteam (logFile)
		reqSB = new RequestSpecBuilder()
										.setBaseUri(getGlobalValues("baseUrl"))
										.addQueryParam("key" , "qaclick123")
										.setContentType(ContentType.JSON)
										.addFilter(RequestLoggingFilter.logRequestTo(logFile)) //This helps in adding of new filter - add all logs requests to logFile 
										.addFilter(ResponseLoggingFilter.logResponseTo(logFile)) //This helps in adding of new filter - add all logs responses to logFile 
										.build();
		System.out.println("Request Spec is Build in Utils");
		return reqSB;

	}

	public ResponseSpecification responseSpec() {
		//Build Response Spec
		respSB = new ResponseSpecBuilder()
									.expectStatusCode(200)
									.expectContentType(ContentType.JSON)
									.build();
		System.out.println("Response Spec is Build in Utils");
		return respSB;
	}
}
