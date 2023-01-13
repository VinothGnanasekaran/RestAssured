package basics;

import static io.restassured.RestAssured.given;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import mockCode_API.Payload;
import mockCode_API.ReusableMethods;

//Sec 7 Lecture 35/36
public class DynamicJson_03_ParameterizationDataSet_DataProvider<dataSetObj> {

	@DataProvider(name ="AddBookDataSet")
	public Object[][] dataSet() {
		return new Object[][] {{"Manj","2609"},{"Vino","2809"},{"Suga","0508"},{"Pappu","1408"},{"Sivasha","0805"}};
		}
	
	
	@Test (dataProvider = "AddBookDataSet")
	public void addBook(String isbn, String aisle) {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		//This example shows, parameterized data is send to the BODY now, and it turns calls - addbookParam method inside Payload, 
				//which will run for all data in dataprovider set
		RequestSpecification request = given().log().all().header("Content-Type", "text/plain")
														.body(Payload.addBookParam(isbn, aisle));
				
		ValidatableResponse response = request.when().post("Library/Addbook.php")
														.then().log().all().assertThat().statusCode(200);
		
		String extractResponse = response.extract().response().asString();
		
		JsonPath js = ReusableMethods.rawStringToJson(extractResponse);
		String bookId = js.getString("ID");
		System.out.println("Added Book Id :"+bookId);

	}
	
	
}
