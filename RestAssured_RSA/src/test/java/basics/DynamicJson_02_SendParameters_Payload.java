package basics;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import mockCode_API.Payload;
import mockCode_API.ReusableMethods;

public class DynamicJson_02_SendParameters_Payload {
	
	@Test
	public void addBook() {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		//This is an example to showcase the BODY accepting the params or sending params from test to Payload method
		
		RequestSpecification request = given().log().all().header("Content-Type", "text/plain")
														.body(Payload.addBookParam("HU","2858"));
		
		ValidatableResponse response = request.when().post("Library/Addbook.php")
														.then().log().all().assertThat().statusCode(200);
		
		String extractResponse = response.extract().response().asString();
		
		JsonPath js = ReusableMethods.rawStringToJson(extractResponse);
		String bookId = js.getString("ID");
		System.out.println("Added Book Id :"+bookId);

	}
	
}

