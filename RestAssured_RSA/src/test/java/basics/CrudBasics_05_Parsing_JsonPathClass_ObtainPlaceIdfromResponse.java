package basics;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class CrudBasics_05_Parsing_JsonPathClass_ObtainPlaceIdfromResponse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//This is an extension of Post Place - we need to get the post id from the response, which is the key input for GetPlace/DeletePlace, etc
		File bodyPath = new File("./src/test/resources/addplace_body.json");
		// TODO Auto-generated method stub
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		/*Since i have imported <-- import static io.restassured.RestAssured.*; -->
				I dont have to start as RestAssured.given() - directly it can start from given method
			*/
		// Spliting one code as Request, Response etc..
		
			RequestSpecification request = given()
																.log().all()
																.queryParam("key", "qaclick123")
																.header("Content-Type", "application/json")
																.body(bodyPath);
			ValidatableResponse response = request
															.when()
																.post("maps/api/place/add/json")		
															.then()
																.log().all()
																.assertThat()
																	.statusLine("HTTP/1.1 200 OK")
																	.body("status", equalTo("OK"));
		//Now we need to extract the response as String 
						String stringResponse = response
																.extract()
																.response()
																.asString();
					//	System.out.println(stringResponse);
		//Now we need to Parse the string response to JSON Class path - Below all JAVA pattern
						JsonPath js = new JsonPath(stringResponse);
						String placeId = js.getString("place_id");
						System.out.println("The New Place Id is :" +placeId);
						
	}

}
