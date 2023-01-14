package basics;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import static io.restassured.RestAssured.*;
import java.util.ArrayList;
import java.util.List;

public class SpecBuilders_POJO_GoogleApi_addPlace {

	public static void main(String[] args) {
				// TODO Auto-generated method stub
			
		//Serialization - Java objects(value setting) & Then convert the javaobjects to JSON payload.
		
		AddPlace a = new AddPlace();
		
		//As long/lat are inside the Location classes, create obj to Location class and access it to set values and 
		//pass the obj of location class to SetLocation in AddPlace class.
			Location loc = new Location();
			loc.setLat(-38.383494);
			loc.setLng(33.427362);
		a.setLocation(loc);
		a.setAccuracy(50);
		a.setName("Love house");
		a.setAddress("29, side layout, cohen 09");
		a.setLanguage("Tamil-IN");
		a.setPhone_number("\"(+91) 983 893 3937");
		a.setWebsite("http://google.com");
		
			List<String> list = new ArrayList<String>();
			list.add("shoe park");
			list.add("shop");
		a.setTypes(list);
		
		
	/*	//Actual Request
		RestAssured.baseURI = "https://rahulshettyacademy.com";
				String respData = given()
					.log().all()
					.queryParam("key", "qaclick123")
					.body(a)
				.when()
					.post("maps/api/place/add/json")
				.then()
					.assertThat().statusCode(200)
					.extract().response().asString();
				System.out.println(respData);
		*/
		
		//The Actual request & Response now build in Spec Builders as below
				//Build Request Spec
				RequestSpecification reqSB = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
																		 .addQueryParam("key" , "qaclick123")
																		 .setContentType(ContentType.JSON)
																		 .build();
				System.out.println("Request Spec is Build");
				
				//Build Response Spec
				ResponseSpecification respSB = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
				System.out.println("Response Spec is Build");
				
				//Build the actual Request
				RequestSpecification request = given().log().all().spec(reqSB).body(a); //Here 'Spec(reqSB)' passed all the header info in it
				System.out.println("Request is Sent");
				
				Response response = request.when().post("maps/api/place/add/json")
															 .then().spec(respSB).extract().response(); //Here 'Spec(respSB)' passed all the needed Body/assertion info in it
				System.out.println("Response is extracted");
				
				String responseString = response.asString();
				System.out.println("Response converted to String : "+responseString);
				
				
	}

}
