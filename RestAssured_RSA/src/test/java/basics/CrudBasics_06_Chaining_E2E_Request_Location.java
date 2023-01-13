package basics;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class CrudBasics_06_Chaining_E2E_Request_Location {

	public static void main(String[] args) throws IOException {
		// TODO File for Body-Add a new place
		File bodyPath = new File("./src/test/resources/addplace_body.json");
		
		// TODO Auto-generated method stub
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		/*Since i have imported <-- import static io.restassured.RestAssured.*; -->
				I dont have to start as RestAssured.given() - directly it can start from given method
			*/
		
		// Spliting one code as Request, Response etc..
		
			RequestSpecification postRequest = given()
																.log().all()
																.queryParam("key", "qaclick123")
	//Method -1: Here the BODY Accepts filepath as variable which is stored within workspace/project (resources) path
														
																//	.body(bodyPath); 

	/*Method -2: Static JSON Payload:
	 *  Here the BODY Accepts file directly from local system, How? 
	 * File content stored as String,  -> Files methods Reads only as Bytes ->convert the bytes data to String to get it readed inside the BODY
	 * Here, Paths.get() - get the path storage in string, & passes to Files
	 * Files.readAllBytes, - reads the content of the file as bytes and keeps
	 *  new Sting() - Converts the inside data into String and passed to BODY
	 *  To USE either Method 1 or Method 2 for Body reading
		*/       											
																.body(new String(Files.readAllBytes(Paths.get("D:\\Vinoth\\API_RESTAssured_RS_Cudemy\\addplace_body.json"))));
			ValidatableResponse postResponse = postRequest
															.when()
																.post("maps/api/place/add/json")		
															.then()
																.log().all()
																.assertThat()
																	.statusLine("HTTP/1.1 200 OK")
																	.body("status", equalTo("OK"));
			System.out.println("Post method - success");	
		//Now we need to extract the response as String 
						String stringPostResponse = postResponse
																		.extract()
																		.response()
																		.asString();
					//	System.out.println(stringResponse);
		//Now we need to Parse the string response to JSON Class path - Below all JAVA pattern
						JsonPath js = new JsonPath(stringPostResponse);
						String placeId_post = js.getString("place_id");
						System.out.println("The New Place Id is :" +placeId_post);
						
		//new GET Request from prev POST- chaining  the place
			RequestSpecification getRequest = given().log().all().queryParam("key", "qaclick123")
																					.queryParam("place_id", placeId_post);
			ValidatableResponse getResponse = getRequest.when().get("maps/api/place/get/json")
																					.then().log().all()
																						.assertThat().statusCode(200).body("name", equalTo("Love house"));
			
			System.out.println("Get method - success");	
		//New PUT request to update the address details of the place
			RequestSpecification putRequest = given().log().all()
																		.queryParam("key", "qa123")
																		.body("{\r\n"+ "\"place_id\":\""+placeId_post+"\",\r\n"
																							+ "\"address\":\"70 Summer walk, USA\",\r\n"
																							+ "\"key\":\"qaclick123\"\r\n"
																							+ "}");
			ValidatableResponse putResponse = putRequest.when().put("maps/api/place/update/json")
																					.then().log().all()
																					.assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
			System.out.println("Put method - success");	
			
	}

}
